package com.dragote.feature.progress.country.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dragote.component.test.coroutines.TestCoroutineExtension
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.entity.CountryStats
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(
    TestCoroutineExtension::class,
)
class CountryProgressViewModelTest {

    private val countryId = "1"
    private val countryStats = CountryStats(
        countryId = countryId,
        progress = 0.5f,
    )
    private val region = Region.EUROPE
    private val country = Country(
        id = countryId,
        name = "Country",
        capital = "Capital",
        drawableRes = 1,
    )

    private val getCountryStatsUseCase: GetCountryStatsUseCase = mock()
    private val getCountriesUseCase: GetCountriesUseCase = mock()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("region" to region))

    private val viewModel = CountryProgressViewModel(
        getCountryStatsUseCase,
        getCountriesUseCase,
        savedStateHandle,
    )

    @Test
    fun `init EXPECT set initial state`() = runTest {
        val expected = CountryProgressState.Initial

        viewModel.state.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `update data EXPECT change initial state to loading state`() = runTest {
        whenever(getCountriesUseCase(region)).thenReturn(listOf(country))
        whenever(getCountryStatsUseCase(countryId)).thenReturn(countryStats)

        viewModel.state.test {
            assertEquals(CountryProgressState.Initial, awaitItem())

            viewModel.updateData()

            assertEquals(CountryProgressState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `data updated EXPECT set content state`() = runTest {
        whenever(getCountriesUseCase(region)).thenReturn(listOf(country))
        whenever(getCountryStatsUseCase(countryId)).thenReturn(countryStats)
        val expected = CountryProgressState.Content(
            listOf(Pair(country, countryStats.progress))
        )

        viewModel.state.test {
            viewModel.updateData()

            assertEquals(expected, expectMostRecentItem())
        }
    }
}
package com.dragote.feature.train.training.domain.scenario

import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.entity.CountryStats
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAnswersScenarioTest {

    private val regions = arrayOf(Region.EUROPE, Region.AFRICA)
    private val countryId = "1"
    private val countries = listOf(
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Albania",
            capital = "Tirana"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Andorra",
            capital = "Andorra la Vella"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Austria",
            capital = "Vienna"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Belarus",
            capital = "Minsk"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Belarus",
            capital = "Minsk"
        ),
    )
    private val countryStats = CountryStats(
        countryId = "1",
        progress = 0.5f,
    )
    private val getCountryStatsUseCase: GetCountryStatsUseCase = mock()
    private val getCountriesUseCase: GetCountriesUseCase = mock()
    private val getAnswersScenario = GetAnswersScenario(
        getCountryStatsUseCase,
        getCountriesUseCase
    )

    @Test
    fun `invoke EXPECT options contains correct country`() = runTest {
        whenever(getCountriesUseCase.invoke(any())).thenReturn(countries)
        whenever(getCountryStatsUseCase.invoke(any())).thenReturn(countryStats)

        val answers = getAnswersScenario(regions, emptyList())

        assertTrue(answers.options.contains(answers.correctCountry))
    }

    @Test
    fun `invoke EXPECT options contains 4 elements`() = runTest {
        whenever(getCountriesUseCase.invoke(any())).thenReturn(countries)
        whenever(getCountryStatsUseCase.invoke(any())).thenReturn(countryStats)

        val answers = getAnswersScenario(regions, emptyList())

        assertEquals(4, answers.options.size)
    }

    @Test
    fun `invoke EXPECT all options are unique`() = runTest {
        whenever(getCountriesUseCase.invoke(any())).thenReturn(countries)
        whenever(getCountryStatsUseCase.invoke(any())).thenReturn(countryStats)

        val answers = getAnswersScenario(regions, emptyList())

        assertEquals(answers.options.distinct(), answers.options)
    }

    @Test
    fun `invoke with completed country EXPECT correct country is not completed country`() = runTest {
        whenever(getCountriesUseCase.invoke(any())).thenReturn(countries)
        whenever(getCountryStatsUseCase.invoke(any())).thenReturn(countryStats)
        val completedCountry = Country(
            id = "1",
            drawableRes = 1,
            name = "Albania",
            capital = "Tirana"
        )

        val answers = getAnswersScenario(regions, listOf(completedCountry))

        assertNotEquals(completedCountry, answers.correctCountry)
    }

    @Test
    fun `invoke when only one uncompleted country EXPECT correct country is uncompleted country`() = runTest {
        val uncompletedCountryId = "42"
        val uncompletedCountry = Country(
            id = uncompletedCountryId,
            drawableRes = 1,
            name = "UncompletedCountry",
            capital = "Capital"
        )
        whenever(getCountriesUseCase.invoke(any())).thenReturn(countries + uncompletedCountry)
        whenever(getCountryStatsUseCase.invoke(uncompletedCountryId)).thenReturn(countryStats.copy(progress = 0.5f))
        whenever(getCountryStatsUseCase.invoke(countryId)).thenReturn(countryStats.copy(progress = 1f))

        val answers = getAnswersScenario(regions, emptyList())

        assertEquals(uncompletedCountry, answers.correctCountry)
    }
}
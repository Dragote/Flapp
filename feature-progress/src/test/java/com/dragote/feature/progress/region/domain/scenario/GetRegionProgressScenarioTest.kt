package com.dragote.feature.progress.region.domain.scenario

import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.entity.CountryStats
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetRegionProgressScenarioTest {

    private val region = Region.EUROPE
    private val firstCountryId = "1"
    private val secondCountryId = "2"
    private val thirdCountryId = "3"
    private val firstCountryStats = CountryStats(
        countryId = firstCountryId,
        progress = 0f
    )
    private val secondCountryStats = CountryStats(
        countryId = secondCountryId,
        progress = 1f
    )
    private val thirdCountryStats = CountryStats(
        countryId = secondCountryId,
        progress = 0.5f
    )
    private val firstCountry = Country(
        id = firstCountryId,
        name = "Country",
        capital = "Capital",
        drawableRes = 1,
    )
    private val secondCountry = Country(
        id = secondCountryId,
        name = "Country",
        capital = "Capital",
        drawableRes = 1,
    )
    private val thirdCountry = Country(
        id = thirdCountryId,
        name = "Country",
        capital = "Capital",
        drawableRes = 1,
    )
    private val countries = listOf(firstCountry, secondCountry, thirdCountry)

    private val getCountryStatsUseCase: GetCountryStatsUseCase = mock()
    private val getCountriesUseCase: GetCountriesUseCase = mock()
    private val scenario = GetRegionProgressScenario(
        getCountriesUseCase,
        getCountryStatsUseCase,
    )

    @Test
    fun `invoke EXPEXT get progress`() = runTest {
        whenever(getCountriesUseCase.invoke(region)).thenReturn(countries)
        whenever(getCountryStatsUseCase.invoke(firstCountryId)).thenReturn(firstCountryStats)
        whenever(getCountryStatsUseCase.invoke(secondCountryId)).thenReturn(secondCountryStats)
        whenever(getCountryStatsUseCase.invoke(thirdCountryId)).thenReturn(thirdCountryStats)

        val expect = 0.5f
        val actual = scenario.invoke(region)

        assertEquals(expect, actual)
    }

    @Test
    fun `invoke with empty countries list EXPEXT exception`() {
        whenever(getCountriesUseCase.invoke(region)).thenReturn(listOf())

        assertThrows(Exception::class.java) {
            runTest {
                scenario.invoke(region)
            }
        }
    }
}
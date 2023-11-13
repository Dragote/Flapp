package com.dragote.feature.train.training.domain.scenario

import com.dragote.feature.train.training.domain.usecase.SetCountryStatsUseCase
import com.dragote.shared.stats.domain.entity.CountryStats
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateCountryProgressScenarioTest {

    private val countryId = "1"
    private val countryStats = CountryStats(
        countryId = countryId,
        progress = 0.5f,
    )
    private val getCountryStatsUseCase: GetCountryStatsUseCase = mock()
    private val setCountryStatsUseCase: SetCountryStatsUseCase = mock()

    private val scenario = UpdateCountryProgressScenario(
        getCountryStatsUseCase,
        setCountryStatsUseCase,
    )

    @Test
    fun `invoke EXPECT set country stats with progress changed on passed value`() = runTest {
        whenever(getCountryStatsUseCase.invoke(countryId)).thenReturn(countryStats)
        val value = 0.25f

        scenario.invoke(countryId, value)

        verify(setCountryStatsUseCase).invoke(countryStats.copy(progress = 0.75f))
    }

    @Test
    fun `invoke and final progress more than 1 EXPECT set country stats with progress is 1`() = runTest {
        whenever(getCountryStatsUseCase.invoke(countryId)).thenReturn(countryStats)
        val value = 0.9f

        scenario.invoke(countryId, value)

        verify(setCountryStatsUseCase).invoke(countryStats.copy(progress = 1f))
    }

    @Test
    fun `invoke and final progress less than 0 EXPECT set country stats with progress is 0`() = runTest {
        whenever(getCountryStatsUseCase.invoke(countryId)).thenReturn(countryStats)
        val value = -0.9f

        scenario.invoke(countryId, value)

        verify(setCountryStatsUseCase).invoke(countryStats.copy(progress = 0f))
    }
}
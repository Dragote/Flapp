package com.dragote.feature.train.training.domain.usecase

import com.dragote.shared.stats.domain.StatsRepository
import com.dragote.shared.stats.domain.entity.CountryStats
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


class SetCountryStatsUseCaseTest {

    private val countryStats = CountryStats(
        countryId = "1",
        progress = 1f,
    )
    private val statsRepository: StatsRepository = mock()
    private val useCase = SetCountryStatsUseCase(statsRepository)

    @Test
    fun `invoke EXPECT set stats by repository`() = runTest {
        useCase.invoke(countryStats)

        verify(statsRepository).setStats(countryStats)
    }
}
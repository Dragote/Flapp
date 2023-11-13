package com.dragote.feature.train.training.domain.usecase

import com.dragote.shared.stats.domain.StatsRepository
import com.dragote.shared.stats.domain.entity.CountryStats
import javax.inject.Inject

class SetCountryStatsUseCase @Inject constructor(
    private val statsRepository: StatsRepository,
) {
    suspend operator fun invoke(countryStats: CountryStats) = statsRepository.setStats(countryStats)
}
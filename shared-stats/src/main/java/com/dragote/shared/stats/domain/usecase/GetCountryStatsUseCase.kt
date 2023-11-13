package com.dragote.shared.stats.domain.usecase

import com.dragote.shared.stats.domain.StatsRepository
import com.dragote.shared.stats.domain.entity.CountryStats
import javax.inject.Inject

class GetCountryStatsUseCase @Inject constructor(
    private val statsRepository: StatsRepository
) {
    suspend operator fun invoke(id: String): CountryStats =
        statsRepository.getStats(id)
}
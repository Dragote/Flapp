package com.dragote.feature.settings.domain

import com.dragote.shared.stats.domain.StatsRepository
import javax.inject.Inject

class ClearProgressUseCase @Inject constructor(
    private val statsRepository: StatsRepository
) {

    suspend operator fun invoke() = statsRepository.clearProgress()
}
package com.dragote.feature.train.training.domain.scenario

import com.dragote.feature.train.training.domain.usecase.SetCountryStatsUseCase
import com.dragote.shared.stats.domain.entity.CountryStats
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import javax.inject.Inject

class UpdateCountryProgressScenario @Inject constructor(
    private val getCountryStatsUseCase: GetCountryStatsUseCase,
    private val setCountryStatsUseCase: SetCountryStatsUseCase,
) {
    companion object {
        const val PROGRESS_COMPLETE = 1f
        const val PROGRESS_ZERO = 0f
    }

    suspend operator fun invoke(countryId: String, value: Float) {
        val currentProgress = getCountryStatsUseCase(countryId).progress
        val finalProgress = when {
            currentProgress + value > PROGRESS_COMPLETE -> PROGRESS_COMPLETE
            currentProgress + value < PROGRESS_ZERO -> PROGRESS_ZERO
            else -> currentProgress + value
        }

        setCountryStatsUseCase(
            CountryStats(
                countryId = countryId,
                progress = finalProgress
            )
        )
    }
}
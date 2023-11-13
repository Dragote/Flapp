package com.dragote.feature.train.trainsettings.presentation

import com.dragote.shared.country.domain.entity.Region

sealed class TrainSettingsState {

    data object Initial : TrainSettingsState()

    data class Content(
        val regions: List<Pair<Region, Boolean>>,
        val sliderValue: Float,
    ) : TrainSettingsState()
}
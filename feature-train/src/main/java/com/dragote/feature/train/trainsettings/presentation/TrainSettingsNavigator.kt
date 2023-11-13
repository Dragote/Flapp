package com.dragote.feature.train.trainsettings.presentation

import com.dragote.shared.country.domain.entity.Region

interface TrainSettingsNavigator {

    fun openTraining(regions: Array<Region>, levels: Int)
}
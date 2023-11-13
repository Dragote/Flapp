package com.dragote.flapp.navigation

import androidx.navigation.NavController
import com.dragote.feature.progress.country.ui.CountryProgressScreenNavArgs
import com.dragote.feature.progress.destinations.CountryProgressScreenDestination
import com.dragote.feature.progress.region.presentation.RegionProgressNavigator
import com.dragote.feature.train.destinations.TrainingScreenDestination
import com.dragote.feature.train.training.presentation.TrainingNavigator
import com.dragote.feature.train.training.ui.TrainingScreenNavArgs
import com.dragote.feature.train.trainsettings.presentation.TrainSettingsNavigator
import com.dragote.shared.country.domain.entity.Region
import com.ramcosta.composedestinations.navigation.navigate

class FlappNavigator(
    private val navController: NavController
) : TrainingNavigator, TrainSettingsNavigator, RegionProgressNavigator {

    override fun openTraining(regions: Array<Region>, levels: Int) {
        navController.navigate(
            TrainingScreenDestination(
                TrainingScreenNavArgs(
                    regions, levels
                )
            )
        )
    }

    override fun closeTraining() {
        navController.navigateUp()
    }

    override fun openCountryProgressScreen(region: Region) {
        navController.navigate(
            CountryProgressScreenDestination(
                CountryProgressScreenNavArgs(region)
            )
        )
    }
}
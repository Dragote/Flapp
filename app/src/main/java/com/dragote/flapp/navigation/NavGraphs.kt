package com.dragote.flapp.navigation

import com.dragote.feature.progress.destinations.CountryProgressScreenDestination
import com.dragote.feature.progress.destinations.RegionProgressScreenDestination
import com.dragote.feature.settings.ui.destinations.SettingsScreenDestination
import com.dragote.feature.train.destinations.TrainSettingsScreenDestination
import com.dragote.feature.train.destinations.TrainingScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object NavGraphs {

    val train = object : NavGraphSpec {
        override val route = "train"
        override val startRoute = TrainSettingsScreenDestination
        override val destinationsByRoute =
            listOf<DestinationSpec<*>>(TrainSettingsScreenDestination, TrainingScreenDestination)
                .associateBy { it.route }
    }

    val progress = object : NavGraphSpec {
        override val route = "progress"
        override val startRoute = RegionProgressScreenDestination
        override val destinationsByRoute =
            listOf<DestinationSpec<*>>(
                RegionProgressScreenDestination,
                CountryProgressScreenDestination
            )
                .associateBy { it.route }
    }

    val settings = object : NavGraphSpec {
        override val route = "settings"
        override val startRoute = SettingsScreenDestination
        override val destinationsByRoute = listOf<DestinationSpec<*>>(SettingsScreenDestination)
            .associateBy { it.route }
    }

    val root = object : NavGraphSpec {
        override val route = "root"
        override val startRoute = train
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(train, progress, settings)
    }
}
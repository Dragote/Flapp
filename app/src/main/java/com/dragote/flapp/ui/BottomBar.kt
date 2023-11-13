package com.dragote.flapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.dragote.feature.progress.destinations.RegionProgressScreenDestination
import com.dragote.feature.settings.ui.destinations.SettingsScreenDestination
import com.dragote.feature.train.destinations.TrainSettingsScreenDestination
import com.dragote.flapp.R
import com.dragote.flapp.navigation.NavGraphs
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: String = navController.currentDestination?.route
        ?: NavGraphs.root.route

    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction.route,
                onClick = {
                    navController.navigate(destination.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(destination.icon, contentDescription = destination.getName()) },
                label = { Text(destination.getName()) },
            )
        }
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
) {
    Progress(RegionProgressScreenDestination, Icons.Default.DonutSmall),
    Train(TrainSettingsScreenDestination, Icons.Default.RocketLaunch),
    Settings(SettingsScreenDestination, Icons.Default.Settings),
}

@Composable
fun BottomBarDestination.getName() =
    when (this) {
        BottomBarDestination.Progress -> stringResource(id = R.string.progress_item)
        BottomBarDestination.Train -> stringResource(id = R.string.train_item)
        BottomBarDestination.Settings -> stringResource(id = R.string.settings_item)
    }
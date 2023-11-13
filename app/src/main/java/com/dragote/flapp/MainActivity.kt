package com.dragote.flapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dragote.flapp.navigation.NavGraphs
import com.dragote.flapp.navigation.currentNavigator
import com.dragote.flapp.navigation.defaultAnimationsForNestedNavGraph
import com.dragote.flapp.ui.BottomBar
import com.dragote.flapp.ui.BottomBarDestination
import com.dragote.shared.settings.UserSettings
import com.dragote.shared.ui.IsDarkTheme
import com.dragote.shared.ui.theme.FlappTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userSettings: UserSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme = userSettings.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
                com.dragote.shared.settings.AppTheme.SYSTEM -> isSystemInDarkTheme()
                com.dragote.shared.settings.AppTheme.LIGHT -> false
                com.dragote.shared.settings.AppTheme.DARK -> true
            }

            CompositionLocalProvider(IsDarkTheme provides useDarkColors) {
                FlappMain(darkTheme = useDarkColors)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun FlappMain(
    darkTheme: Boolean
) {
    FlappTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        val currentDestination = navController.currentDestinationAsState().value?.route
        val showBottomBar = enumValues<BottomBarDestination>().any {
            it.direction.route == currentDestination
        }

        Scaffold(
            bottomBar = {
                AnimatedContent(
                    targetState = showBottomBar, label = "BottomBarAnimatedContent",
                ) { isVisible ->
                    if (isVisible) {
                        BottomBar(navController)
                    }
                }
            }
        ) { contentPadding ->
            DestinationsNavHost(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                navGraph = NavGraphs.root,
                engine = rememberAnimatedNavHostEngine(
                    defaultAnimationsForNestedNavGraph = defaultAnimationsForNestedNavGraph
                ),
                dependenciesContainerBuilder = {
                    dependency(currentNavigator())
                }
            )
        }
    }
}
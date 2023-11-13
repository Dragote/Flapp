package com.dragote.flapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder

fun DependenciesContainerBuilder<*>.currentNavigator(): FlappNavigator =
    FlappNavigator(navController = navController)

val defaultNavAnimations = NestedNavGraphDefaultAnimations(
    enterTransition = { fadeIn(animationSpec = tween(400)) },
    exitTransition = { fadeOut(animationSpec = tween(400)) }
)

val defaultAnimationsForNestedNavGraph = mapOf(
    NavGraphs.settings to defaultNavAnimations,
    NavGraphs.progress to defaultNavAnimations,
    NavGraphs.train to defaultNavAnimations,
)
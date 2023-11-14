package com.dragote.feature.progress.country.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragote.feature.progress.country.presentation.CountryProgressState
import com.dragote.feature.progress.country.presentation.CountryProgressViewModel
import com.dragote.shared.country.domain.entity.Region
import com.ramcosta.composedestinations.annotation.Destination
import java.io.Serializable

class CountryProgressScreenNavArgs(val region: Region) : Serializable

@Destination(
    navArgsDelegate = CountryProgressScreenNavArgs::class
)
@Composable
fun CountryProgressScreen(
    modifier: Modifier = Modifier,
    viewModel: CountryProgressViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.updateData()
    }

    Box {
        AnimatedContent(
            targetState = state,
            label = "CountryProgressScreenAnimatedContent",
        ) { currentState ->
            when (currentState) {
                CountryProgressState.Initial -> Unit
                CountryProgressState.Loading -> LoadingState()
                is CountryProgressState.Content -> ContentState(
                    modifier = modifier,
                    state = currentState,
                )
            }
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ContentState(
    modifier: Modifier = Modifier,
    state: CountryProgressState.Content,
) {
    CountryProgressList(
        modifier = modifier,
        countries = state.countriesWithProgress,
    )
}
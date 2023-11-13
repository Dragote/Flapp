package com.dragote.feature.progress.region.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragote.feature.progress.region.presentation.RegionProgressNavigator
import com.dragote.feature.progress.region.presentation.RegionProgressState
import com.dragote.feature.progress.region.presentation.RegionProgressViewModel
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.getName
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun RegionProgressScreen(
    navigator: RegionProgressNavigator,
    viewModel: RegionProgressViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.updateData()
    }

    Box {
        AnimatedContent(
            targetState = state,
            label = "RegionProgressScreenAnimatedContent",
        ) { progressState ->
            when (progressState) {
                RegionProgressState.Initial -> Unit
                RegionProgressState.Loading -> LoadingState()
                is RegionProgressState.Content -> ContentState(
                    state = progressState,
                    onRegionClick = navigator::openCountryProgressScreen
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
    state: RegionProgressState.Content,
    onRegionClick: (Region) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(state.regions) { region ->
            RegionCard(
                name = region.region.getName(),
                progress = region.progress,
                onClick = { onRegionClick(region.region) }
            )
        }
    }
}
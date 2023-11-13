package com.dragote.feature.train.trainsettings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragote.feature.train.R
import com.dragote.feature.train.trainsettings.presentation.TrainSettingsNavigator
import com.dragote.feature.train.trainsettings.presentation.TrainSettingsState
import com.dragote.feature.train.trainsettings.presentation.TrainSettingsViewModel
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.getName
import com.ramcosta.composedestinations.annotation.Destination
import kotlin.math.roundToInt
import com.dragote.shared.ui.R as UIRes

@Destination(start = true)
@Composable
fun TrainSettingsScreen(
    navigator: TrainSettingsNavigator,
    viewModel: TrainSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        TrainSettingsState.Initial -> Unit

        is TrainSettingsState.Content -> TrainSettingsContentState(
            state = state as TrainSettingsState.Content,
            navigator = navigator,
            updateChipState = viewModel::updateRegionChipState,
            changeSliderValue = viewModel::changeSliderValue,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainSettingsContentState(
    modifier: Modifier = Modifier,
    state: TrainSettingsState.Content,
    navigator: TrainSettingsNavigator,
    updateChipState: (Region) -> Unit,
    changeSliderValue: (Float) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = UIRes.drawable.ic_globe),
            contentDescription = stringResource(id = R.string.globe),
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
        )
        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.titleLarge
        )
        FlowRow(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 3
        ) {
            state.regions.forEach { chipData ->
                CountryFilterChip(
                    chipData = chipData,
                    onClick = { updateChipState(chipData.first) }
                )
            }
        }

        SliderWithText(
            sliderValue = state.sliderValue,
            onSliderValueChange = changeSliderValue,
        )

        Button(
            modifier = Modifier,
            onClick = {
                val selectedRegions = state.regions.filter {
                    it.second
                }.map {
                    it.first
                }

                navigator.openTraining(
                    regions = selectedRegions.toTypedArray(),
                    levels = state.sliderValue.toRoundsCount()
                )
            },
            enabled = state.regions.any {
                it.second
            }
        ) {
            Text(
                text = stringResource(id = R.string.start_button),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun SliderWithText(
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.slider_text, sliderValue.toRoundsCount()),
            style = TextStyle(
                fontSize = 18.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = sliderValue,
            onValueChange = {
                onSliderValueChange(it)
            },
            valueRange = 0f..1f,
            steps = 4,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryFilterChip(
    modifier: Modifier = Modifier,
    chipData: Pair<Region, Boolean>,
    onClick: () -> Unit,
) {
    FilterChip(
        modifier = modifier,
        selected = chipData.second,
        onClick = onClick,
        label = {
            Text(
                text = chipData.first.getName(),
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}

fun Float.toRoundsCount(): Int = ((this * 100).roundToInt() / 4) + 5
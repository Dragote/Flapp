package com.dragote.feature.train.training.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragote.feature.train.R
import com.dragote.feature.train.training.presentation.TrainingNavigator
import com.dragote.feature.train.training.presentation.TrainingState
import com.dragote.feature.train.training.presentation.TrainingViewModel
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.ui.IsDarkTheme
import com.dragote.shared.ui.components.AlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import java.io.Serializable

class TrainingScreenNavArgs(val regions: Array<Region>, val rounds: Int) : Serializable

@Destination(
    navArgsDelegate = TrainingScreenNavArgs::class
)
@Composable
fun TrainingScreen(
    navigator: TrainingNavigator,
    viewModel: TrainingViewModel = hiltViewModel()
) {
    TrainingState(navigator, viewModel)
    TrainingAlert(navigator)
}

@Composable
fun TrainingState(
    navigator: TrainingNavigator,
    viewModel: TrainingViewModel,
) {
    val state by viewModel.state.collectAsState()
    val background = if (IsDarkTheme.current) {
        R.drawable.flags_background_dark
    } else {
        R.drawable.flags_background
    }
    val transitionDuration = 1000
    val enterTransition = slideInVertically(
        initialOffsetY = { 5 * it },
        animationSpec = tween(transitionDuration),
    )
    val exitTransition = slideOutVertically(
        targetOffsetY = { 5 * it },
        animationSpec = tween(transitionDuration),
    )
    val flagZonePaddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 42.dp)
    var flagpoleVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        flagpoleVisible = true
        viewModel.setNextRound()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = background),
                contentScale = ContentScale.FillWidth,
            ),
    ) {
        Flagpole(
            modifier = Modifier.padding(flagZonePaddingValues),
            visible = flagpoleVisible,
            enterTransition = enterTransition,
        )

        AnimatedContent(
            targetState = state,
            label = "TrainingScreenAnimatedContent",
            contentKey = { targetState ->
                when (targetState) {
                    is TrainingState.Content -> "Content"
                    is TrainingState.Final -> "Final"
                    else -> "Initial"
                }
            },
            transitionSpec = {
                if (state is TrainingState.Content) {
                    enterTransition togetherWith exitTransition
                } else {
                    enterTransition togetherWith exitTransition
                }
            },
        ) { targetState ->
            when (targetState) {
                TrainingState.Initial -> Unit
                is TrainingState.Content -> TrainingContent(
                    state = targetState,
                    onNextRound = { viewModel.setNextRound() },
                    correctClick = { viewModel.successfullyComplete() },
                    incorrectClick = { viewModel.unsuccessfullyComplete() },
                    flagZonePaddings = flagZonePaddingValues,
                )

                is TrainingState.Final -> TrainingFinal(
                    state = targetState,
                    goBackClick = { navigator.closeTraining() },
                    flagZonePaddings = flagZonePaddingValues,
                )
            }
        }
    }
}

@Composable
fun TrainingContent(
    state: TrainingState.Content,
    onNextRound: () -> Unit,
    correctClick: () -> Unit,
    incorrectClick: () -> Unit,
    flagZonePaddings: PaddingValues
) {
    var flagVisible by remember { mutableStateOf(false) }

    val flagOffset = animateFloatAsState(
        targetValue = if (flagVisible) 0f else 3000f,
        animationSpec = tween(700),
        label = "FlagOffsetAnimation",
        finishedListener = {
            if (!flagVisible) {
                onNextRound()
            }
        }
    )
    val answerButtonEnabled = flagOffset.value == 0f

    ImageFlag(
        modifier = Modifier.padding(flagZonePaddings),
        flag = state.answers.correctCountry.drawableRes,
        offset = flagOffset.value,
        onSuccess = { flagVisible = true }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${state.currentRound}/${state.rounds}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            val progress = animateFloatAsState(
                targetValue = state.currentRound.toFloat() / state.rounds,
                label = "ProgressIndicatorAnimation"
            )
            LinearProgressIndicator(progress = progress.value)
        }
        ButtonZone(
            answers = state.answers.options.map { it.name },
            correctAnswer = state.answers.correctCountry.name,
            answerButtonEnabled = answerButtonEnabled,
            nextStepClick = {
                flagVisible = false
            },
            correctClick = {
                correctClick()
            },
            incorrectClick = {
                incorrectClick()
            },
        )
    }
}

@Composable
fun TrainingFinal(
    state: TrainingState.Final,
    goBackClick: () -> Unit,
    flagZonePaddings: PaddingValues,
) {
    var resultCardVisible by remember { mutableStateOf(false) }

    val resultCardOffset = animateFloatAsState(
        targetValue = if (resultCardVisible) 0f else 3000f,
        animationSpec = tween(700),
        label = "ResultCardOffsetAnimation",
    )

    LaunchedEffect(Unit) {
        delay(500)
        resultCardVisible = true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Card(
            modifier = Modifier
                .padding(flagZonePaddings)
                .fillMaxWidth()
                .padding(start = 25.dp, top = 25.dp)
                .graphicsLayer { translationY = resultCardOffset.value },
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.result_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.result_text, state.result, state.rounds),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 54.sp,
            )
        }

        Button(
            modifier = Modifier.padding(bottom = 84.dp),
            onClick = goBackClick,
        ) {
            Text(text = stringResource(id = R.string.close_button))
        }
    }
}

@Composable
fun TrainingAlert(
    navigator: TrainingNavigator,
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        openAlertDialog = true
    }

    if (openAlertDialog) {
        AlertDialog(
            onDismiss = {
                openAlertDialog = false
            },
            onConfirm = {
                openAlertDialog = false
                navigator.closeTraining()
            },
            title = stringResource(id = R.string.alert_title),
            confirmText = stringResource(id = R.string.alert_confirm),
            dismissText = stringResource(id = R.string.alert_dismiss),
            icon = Icons.Default.Info
        )
    }
}
package com.dragote.feature.train.training.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dragote.feature.train.R
import com.dragote.shared.ui.IsDarkTheme
import com.dragote.shared.ui.theme.md_theme_dark_green
import com.dragote.shared.ui.theme.md_theme_light_green

@Composable
fun ButtonZone(
    modifier: Modifier = Modifier,
    answerButtonEnabled: Boolean,
    answers: List<String>,
    correctAnswer: String,
    nextStepClick: () -> Unit,
    correctClick: () -> Unit,
    incorrectClick: () -> Unit,
) {
    var colorized by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.padding(16.dp))

        answers.forEach { answer ->
            AnswerButton(
                answer = answer,
                correctAnswer = correctAnswer,
                colorized = colorized,
                enabled = answerButtonEnabled,
                onClick = { colorized = true },
                correctClick = correctClick,
                incorrectClick = incorrectClick,
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                nextStepClick()
                colorized = false
            },
            enabled = colorized,
        ) {
            Text(text = stringResource(id = R.string.next_button))
        }
    }
}

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    answer: String,
    correctAnswer: String,
    colorized: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    correctClick: () -> Unit,
    incorrectClick: () -> Unit,
) {
    val buttonColor by animateColorAsState(
        targetValue = getAnswerButtonColor(
            answer = answer,
            correctAnswer = correctAnswer,
            colorized = colorized
        ),
        label = "ButtonColorAnimation",
    )

    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {
            if (!colorized) {
                onClick()
                if (answer == correctAnswer) {
                    correctClick()
                } else {
                    incorrectClick()
                }
            }
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(
            text = answer,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun getAnswerButtonColor(
    answer: String,
    correctAnswer: String,
    colorized: Boolean,
): Color = when {
    colorized && answer == correctAnswer -> if (IsDarkTheme.current) {
        md_theme_dark_green
    } else {
        md_theme_light_green
    }

    colorized && answer != correctAnswer -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.secondary
}
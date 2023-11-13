package com.dragote.feature.train.training.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Flagpole(
    modifier: Modifier = Modifier,
    visible: Boolean,
    enterTransition: EnterTransition,
) {
    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
    ) {
        val canvasOffset = Offset(30f, 30f)

        Canvas(modifier = modifier.fillMaxSize(),
            onDraw = {
                drawCircle(color = Color.Black, radius = 30f, center = canvasOffset)
                drawLine(
                    color = Color.Black,
                    start = canvasOffset,
                    end = canvasOffset.copy(y = size.height),
                    strokeWidth = 10.dp.toPx()
                )
            }
        )
    }
}
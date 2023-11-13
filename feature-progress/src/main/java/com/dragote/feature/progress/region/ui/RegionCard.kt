package com.dragote.feature.progress.region.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RegionCard(
    modifier: Modifier = Modifier,
    name: String,
    progress: Float,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .shadow(16.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ProgressIndicator(progress = progress)

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .wrapContentHeight(),
                text = name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color = ProgressIndicatorDefaults.circularColor
) {
    Box(
        modifier = modifier.size(74.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.fillMaxSize(),
            progress = progress,
            color = color,
            trackColor = MaterialTheme.colorScheme.surface,
        )
        Text(
            text = "${(progress * 100).toInt()}%", style = MaterialTheme.typography.titleLarge
        )
    }
}
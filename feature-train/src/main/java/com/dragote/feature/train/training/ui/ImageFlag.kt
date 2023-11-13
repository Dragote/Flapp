package com.dragote.feature.train.training.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageFlag(
    modifier: Modifier = Modifier,
    offset: Float,
    flag: Int,
    onSuccess: () -> Unit,
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(start = 25.dp, top = 25.dp)
            .graphicsLayer { translationY = offset },
        contentScale = ContentScale.Fit,
        alignment = Alignment.TopStart,
        painter = rememberAsyncImagePainter(
            model = flag,
            onSuccess = { onSuccess() },
        ),
        contentDescription = null,
    )
}
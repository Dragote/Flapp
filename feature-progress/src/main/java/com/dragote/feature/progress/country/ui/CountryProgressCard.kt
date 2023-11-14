package com.dragote.feature.progress.country.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dragote.feature.progress.region.ui.ProgressIndicator
import com.dragote.shared.country.domain.entity.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryProgressCard(
    modifier: Modifier = Modifier,
    country: Country,
    progress: Float,
) {
    var flagExpanded by rememberSaveable { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                flagExpanded = !flagExpanded
                coroutineScope.launch {
                    delay(200)
                    bringIntoViewRequester.bringIntoView()
                }
            },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            AnimatedVisibility(visible = !flagExpanded) {
                Image(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(80.dp)
                        .height(80.dp),
                    contentScale = ContentScale.Fit,
                    painter = rememberAsyncImagePainter(country.drawableRes),
                    contentDescription = null,
                )
            }

            CountryInfo(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                country = country
            )

            ProgressIndicator(progress = progress)
        }
        AnimatedVisibility(visible = flagExpanded) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Fit,
                painter = rememberAsyncImagePainter(country.drawableRes),
                contentDescription = null,
            )
        }
    }
}
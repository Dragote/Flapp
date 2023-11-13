package com.dragote.feature.progress.country.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dragote.shared.country.domain.entity.Country

@Composable
fun CountryProgressList(
    modifier: Modifier = Modifier,
    countries: List<Pair<Country, Float>>,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        state = LazyListState()
    ) {
        items(
            countries
        ) { country ->
            CountryProgressCard(
                country = country.first,
                progress = country.second,
            )
        }
    }
}
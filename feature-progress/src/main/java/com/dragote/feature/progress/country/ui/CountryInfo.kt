package com.dragote.feature.progress.country.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dragote.shared.country.domain.entity.Country

@Composable
fun CountryInfo(modifier: Modifier = Modifier, country: Country) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = country.name,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = country.capital,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
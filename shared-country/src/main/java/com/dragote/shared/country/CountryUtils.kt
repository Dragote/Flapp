package com.dragote.shared.country

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dragote.shared.country.domain.entity.Region

@Composable
fun Region.getName(): String =
    when (this) {
        Region.EUROPE -> stringResource(R.string.region_europe)
        Region.ASIA -> stringResource(R.string.region_asia)
        Region.NORTH_AMERICA -> stringResource(R.string.region_north_america)
        Region.SOUTH_AMERICA -> stringResource(R.string.region_south_america)
        Region.AFRICA -> stringResource(R.string.region_africa)
        Region.OCEANIA -> stringResource(R.string.region_oceania)
    }
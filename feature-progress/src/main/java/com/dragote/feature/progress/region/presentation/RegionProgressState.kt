package com.dragote.feature.progress.region.presentation

import com.dragote.shared.country.domain.entity.Region

sealed class RegionProgressState {

    data object Initial : RegionProgressState()

    data object Loading : RegionProgressState()

    data class Content(
        val regions: List<RegionInfo>,
    ) : RegionProgressState()
}

data class RegionInfo(
    val region: Region,
    val progress: Float,
)
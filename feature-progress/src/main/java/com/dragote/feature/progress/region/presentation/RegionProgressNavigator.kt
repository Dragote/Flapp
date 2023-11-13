package com.dragote.feature.progress.region.presentation

import com.dragote.shared.country.domain.entity.Region

interface RegionProgressNavigator {

    fun openCountryProgressScreen(region: Region)
}
package com.dragote.feature.progress.country.presentation

import com.dragote.shared.country.domain.entity.Country

sealed class CountryProgressState {

    data object Initial : CountryProgressState()

    data object Loading : CountryProgressState()

    data class Content(
        val countriesWithProgress: List<Pair<Country, Float>>
    ) : CountryProgressState()
}
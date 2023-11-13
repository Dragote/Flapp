package com.dragote.feature.progress.country.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragote.feature.progress.country.ui.CountryProgressScreenNavArgs
import com.dragote.feature.progress.navArgs
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryProgressViewModel @Inject constructor(
    private val getCountryStatsUseCase: GetCountryStatsUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<CountryProgressScreenNavArgs>()

    private val _state = MutableStateFlow<CountryProgressState>(CountryProgressState.Initial)
    val state: StateFlow<CountryProgressState> = _state

    fun updateData() {
        _state.value = CountryProgressState.Loading

        viewModelScope.launch {
            val countries = getCountriesUseCase(navArgs.region)

            _state.value = CountryProgressState.Content(
                countries.map { country ->
                    country to getCountryStatsUseCase(country.id).progress
                },
            )
        }
    }
}
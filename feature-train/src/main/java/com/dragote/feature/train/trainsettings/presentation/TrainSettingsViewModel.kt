package com.dragote.feature.train.trainsettings.presentation

import androidx.lifecycle.ViewModel
import com.dragote.shared.country.domain.entity.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TrainSettingsViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val INITIAL_SLIDER_VALUE = 0.2f
    }

    private val _state = MutableStateFlow<TrainSettingsState>(TrainSettingsState.Initial)
    val state: StateFlow<TrainSettingsState> = _state

    init {
        setupState()
    }

    private fun setupState() {
        _state.value = TrainSettingsState.Content(
            regions = Region.values().map { region -> region to false },
            sliderValue = INITIAL_SLIDER_VALUE,
        )
    }

    fun updateRegionChipState(type: Region) {
        val state = _state.value as TrainSettingsState.Content
        val updatedList =
            state.regions.map { (countryType, chipEnabled) ->
                if (countryType == type) {
                    countryType to !chipEnabled
                } else {
                    countryType to chipEnabled
                }
            }
        _state.value = state.copy(regions = updatedList)
    }

    fun changeSliderValue(value: Float) {
        _state.value = (_state.value as TrainSettingsState.Content).copy(sliderValue = value)
    }
}
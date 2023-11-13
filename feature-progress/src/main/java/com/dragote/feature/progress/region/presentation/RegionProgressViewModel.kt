package com.dragote.feature.progress.region.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragote.shared.country.domain.entity.Region
import com.dragote.feature.progress.region.domain.scenario.GetRegionProgressScenario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionProgressViewModel @Inject constructor(
    private val getRegionProgressScenario: GetRegionProgressScenario,
) : ViewModel() {

    private val _state = MutableStateFlow<RegionProgressState>(RegionProgressState.Initial)
    val state: StateFlow<RegionProgressState> = _state

    fun updateData() {
        _state.value = RegionProgressState.Loading

        viewModelScope.launch {
            _state.value = RegionProgressState.Content(
                Region.values().map { region ->
                    RegionInfo(
                        region = region,
                        progress = getRegionProgressScenario(region),
                    )
                }
            )
        }
    }
}
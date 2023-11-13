package com.dragote.feature.train.training.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragote.feature.train.navArgs
import com.dragote.feature.train.training.domain.scenario.GetAnswersScenario
import com.dragote.feature.train.training.domain.scenario.UpdateCountryProgressScenario
import com.dragote.feature.train.training.ui.TrainingScreenNavArgs
import com.dragote.shared.country.domain.entity.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val getAnswersScenario: GetAnswersScenario,
    private val updateCountryProgressScenario: UpdateCountryProgressScenario,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        const val INCREASE_VALUE = 0.2f
        const val DECREASE_VALUE = -0.2f
    }

    private val _state = MutableStateFlow<TrainingState>(TrainingState.Initial)
    val state: StateFlow<TrainingState> = _state

    private val navArgs: TrainingScreenNavArgs = savedStateHandle.navArgs()

    private var currentRound = 0
    private var result = 0

    private val completedCountries = mutableListOf<Country>()

    fun setNextRound() {
        if (currentRound == navArgs.rounds) {
            completedCountries.clear()
            _state.value = TrainingState.Final(result, navArgs.rounds)
        } else {
            viewModelScope.launch {
                generateRound()
            }
        }
    }

    private suspend fun generateRound() {
        currentRound++

        val answers = getAnswersScenario(
            regions = navArgs.regions,
            completedCountries = completedCountries,
        )

        _state.value = TrainingState.Content(
            currentRound = currentRound,
            rounds = navArgs.rounds,
            answers = answers,
        )
    }

    fun successfullyComplete() {
        if (_state.value !is TrainingState.Content) return

        result++

        updateCountryProgress(INCREASE_VALUE)
    }

    fun unsuccessfullyComplete() {
        if (_state.value !is TrainingState.Content) return

        updateCountryProgress(DECREASE_VALUE)
    }

    private fun updateCountryProgress(value: Float) {
        viewModelScope.launch {
            val correctCountry = (_state.value as TrainingState.Content).answers.correctCountry

            updateCountryProgressScenario(correctCountry.id, value)

            completedCountries.add(correctCountry)
        }
    }
}
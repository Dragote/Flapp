package com.dragote.feature.train.training.presentation

import com.dragote.feature.train.training.domain.entity.Answers

sealed class TrainingState {

    data object Initial : TrainingState()

    data class Content(
        val currentRound: Int,
        val rounds: Int,
        val answers: Answers,
    ) : TrainingState()

    data class Final(val result: Int, val rounds: Int) : TrainingState()
}
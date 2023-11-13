package com.dragote.feature.train.training.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dragote.component.test.coroutines.TestCoroutineExtension
import com.dragote.feature.train.training.domain.entity.Answers
import com.dragote.feature.train.training.domain.scenario.GetAnswersScenario
import com.dragote.feature.train.training.domain.scenario.UpdateCountryProgressScenario
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.clearInvocations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(
    TestCoroutineExtension::class,
)
class TrainingViewModelTest {

    companion object {
        const val INCREASE_VALUE = 0.2f
        const val DECREASE_VALUE = -0.2f
    }

    private val regions = arrayOf(Region.EUROPE)
    private val rounds = 2
    private val countryId = "1"
    private val correctCountry = Country(
        id = countryId,
        drawableRes = 1,
        name = "Albania",
        capital = "Tirana"
    )

    private val options = listOf(
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Andorra",
            capital = "Andorra la Vella"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Austria",
            capital = "Vienna"
        ),
        Country(
            id = countryId,
            drawableRes = 1,
            name = "Belarus",
            capital = "Minsk"
        )
    ) + correctCountry
    private val answers = Answers(correctCountry, options)

    private val getAnswersScenario: GetAnswersScenario = mock()
    private val updateCountryProgressScenario: UpdateCountryProgressScenario = mock()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(
        mapOf(
            "regions" to regions,
            "rounds" to rounds,
        )
    )

    private val viewModel = TrainingViewModel(
        getAnswersScenario,
        updateCountryProgressScenario,
        savedStateHandle
    )

    @Test
    fun `init EXPECT set initial state`() = runTest {
        viewModel.state.test {
            assertEquals(TrainingState.Initial, awaitItem())
        }
    }

    @Test
    fun `set next round EXPECT set content state with next round`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)
        val contentState = TrainingState.Content(
            currentRound = 1,
            rounds = rounds,
            answers = answers
        )

        viewModel.setNextRound()

        viewModel.state.test {
            assertEquals(contentState, expectMostRecentItem())
        }
    }

    @Test
    fun `set next round and current round is final EXPECT set final state`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()
        viewModel.setNextRound()
        viewModel.setNextRound()

        viewModel.state.test {
            assertEquals(TrainingState.Final(result = 0, rounds = rounds), expectMostRecentItem())
        }
    }

    @Test
    fun `set next round with no completed country EXPECT get answers with no completed country`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()

        verify(getAnswersScenario).invoke(regions = regions, completedCountries = emptyList())
    }

    @Test
    fun `set next round with completed country EXPECT get answers with completed country`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()
        clearInvocations(getAnswersScenario)

        viewModel.successfullyComplete()
        viewModel.setNextRound()

        verify(getAnswersScenario).invoke(regions = regions, completedCountries = listOf(answers.correctCountry))
    }

    @Test
    fun `successfully complete EXPECT update country progress by increase value`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()
        viewModel.successfullyComplete()

        verify(updateCountryProgressScenario).invoke(answers.correctCountry.id, INCREASE_VALUE)
    }

    @Test
    fun `unsuccessfully complete EXPECT update country progress by decrease value`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()
        viewModel.unsuccessfullyComplete()

        verify(updateCountryProgressScenario).invoke(answers.correctCountry.id, DECREASE_VALUE)
    }

    @Test
    fun `two rounds was completed successfully EXPECT set final state with result is 2`() = runTest {
        whenever(getAnswersScenario.invoke(any(), any())).thenReturn(answers)

        viewModel.setNextRound()
        viewModel.successfullyComplete()
        viewModel.setNextRound()
        viewModel.successfullyComplete()
        viewModel.setNextRound()

        viewModel.state.test {
            assertEquals(TrainingState.Final(result = 2, rounds = rounds), expectMostRecentItem())
        }
    }
}
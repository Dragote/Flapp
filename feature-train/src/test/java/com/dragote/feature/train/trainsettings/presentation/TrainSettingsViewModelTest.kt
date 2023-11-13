package com.dragote.feature.train.trainsettings.presentation

import app.cash.turbine.test
import com.dragote.shared.country.domain.entity.Region
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TrainSettingsViewModelTest {

    private val initialSliderValue = 0.2f
    private val viewModel = TrainSettingsViewModel()

    @Test
    fun `init EXPECT set content state with initial values`() = runTest {
        val contentState = TrainSettingsState.Content(
            regions = listOf(
                Region.EUROPE to false,
                Region.ASIA to false,
                Region.NORTH_AMERICA to false,
                Region.SOUTH_AMERICA to false,
                Region.AFRICA to false,
                Region.OCEANIA to false,
            ),
            sliderValue = initialSliderValue,
        )

        viewModel.state.test {
            assertEquals(contentState, awaitItem())
        }
    }

    @Test
    fun `update region chip state EXPEXT content state with updated region chip state`() = runTest {
        val expected = TrainSettingsState.Content(
            regions = listOf(
                Region.EUROPE to false,
                Region.ASIA to false,
                Region.NORTH_AMERICA to true,
                Region.SOUTH_AMERICA to false,
                Region.AFRICA to false,
                Region.OCEANIA to false,
            ),
            sliderValue = initialSliderValue,
        )

        viewModel.state.test {
            viewModel.updateRegionChipState(Region.NORTH_AMERICA)
            assertEquals(expected, expectMostRecentItem())
        }
    }

    @Test
    fun `change slider value EXPEXT content state with new slider value`() = runTest {
        val sliderValue = 0.5f
        val expected = TrainSettingsState.Content(
            regions = listOf(
                Region.EUROPE to false,
                Region.ASIA to false,
                Region.NORTH_AMERICA to false,
                Region.SOUTH_AMERICA to false,
                Region.AFRICA to false,
                Region.OCEANIA to false,
            ),
            sliderValue = sliderValue,
        )

        viewModel.state.test {
            viewModel.changeSliderValue(sliderValue)
            assertEquals(expected, expectMostRecentItem())
        }
    }
}
package com.dragote.feature.progress.region.presentation

import app.cash.turbine.test
import com.dragote.component.test.coroutines.TestCoroutineExtension
import com.dragote.feature.progress.region.domain.scenario.GetRegionProgressScenario
import com.dragote.shared.country.domain.entity.Region
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(
    TestCoroutineExtension::class,
)
class RegionProgressViewModelTest {

    private val getRegionProgressScenario: GetRegionProgressScenario = mock()

    private val viewModel = RegionProgressViewModel(
        getRegionProgressScenario
    )

    @Test
    fun `init EXPECT set initial state`() = runTest {
        val expected = RegionProgressState.Initial

        viewModel.state.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `update data EXPECT change initial state to loading state`() = runTest {
        whenever(getRegionProgressScenario(any())).thenReturn(1f)

        viewModel.state.test {
            assertEquals(RegionProgressState.Initial, awaitItem())

            viewModel.updateData()

            assertEquals(RegionProgressState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `data updated EXPECT set content state`() = runTest {
        whenever(getRegionProgressScenario(any())).thenReturn(1f)

        val expected = RegionProgressState.Content(
            listOf(
                RegionInfo(
                    region = Region.EUROPE,
                    progress = 1f,
                ),
                RegionInfo(
                    region = Region.ASIA,
                    progress = 1f,
                ),
                RegionInfo(
                    region = Region.NORTH_AMERICA,
                    progress = 1f,
                ),
                RegionInfo(
                    region = Region.SOUTH_AMERICA,
                    progress = 1f,
                ),
                RegionInfo(
                    region = Region.AFRICA,
                    progress = 1f,
                ),
                RegionInfo(
                    region = Region.OCEANIA,
                    progress = 1f,
                )
            )
        )

        viewModel.state.test {
            viewModel.updateData()

            assertEquals(expected, expectMostRecentItem())
        }
    }
}
package com.dragote.shared.stats.domain.usecase

import com.dragote.shared.stats.domain.StatsRepository
import com.dragote.shared.stats.domain.entity.CountryStats
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCountryStatsUseCaseTest {

    private val countryId = "1"
    private val stats = CountryStats(
        countryId = countryId,
        progress = 0.5f
    )

    private val repository: StatsRepository = mock()
    private val useCase = GetCountryStatsUseCase(repository)

    @Test
    fun `invoke EXPEXT get stats`() = runTest {
        whenever(repository.getStats(countryId)).thenReturn(stats)

        assertEquals(stats, useCase.invoke(countryId))
    }
}
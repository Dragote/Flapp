package com.dragote.shared.stats.data

import com.dragote.shared.stats.domain.entity.CountryStats
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StatsRepositoryImplTest {

    private val countryId = "1"
    private val progress = 0.5f
    private val countryStats = CountryStats(
        countryId = countryId,
        progress = progress,
    )
    private val statsDao: StatsDao = mock()
    private val repository = StatsRepositoryImpl(statsDao)

    @Test
    fun `set stats EXPECT insert stats by stats dao`() = runTest {
        repository.setStats(countryStats)

        verify(statsDao).insertStats(Stats(countryId, progress))
    }

    @Test
    fun `get stats EXPECT country stats`() = runTest {
        whenever(statsDao.loadSingle(countryId)).thenReturn(Stats(countryId, progress))
        val expected = CountryStats(countryId, progress)
        val actual = repository.getStats(countryId)

        assertEquals(expected, actual)
    }

    @Test
    fun `get stats with no data from stats dao EXPECT country stats with 0 progress`() = runTest {
        whenever(statsDao.loadSingle(countryId)).thenReturn(null)
        val expected = CountryStats(countryId, 0f)
        val actual = repository.getStats(countryId)

        assertEquals(expected, actual)
    }

    @Test
    fun `get stats EXPECT load stats by stats dao`() = runTest {
        whenever(statsDao.loadSingle(countryId)).thenReturn(Stats(countryId, progress))

        repository.getStats(countryId)

        verify(statsDao).loadSingle(countryId)
    }

    @Test
    fun `clear progress EXPECT clear by stats dao`() = runTest {
        repository.clearProgress()

        verify(statsDao).clear()
    }
}
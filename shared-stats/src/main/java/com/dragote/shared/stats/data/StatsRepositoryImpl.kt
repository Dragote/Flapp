package com.dragote.shared.stats.data

import com.dragote.shared.stats.domain.StatsRepository
import com.dragote.shared.stats.domain.entity.CountryStats
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override suspend fun setStats(countryStats: CountryStats) {
        statsDao.insertStats(countryStats.toStats())
    }

    override suspend fun getStats(id: String): CountryStats =
        statsDao.loadSingle(id).toCountryStats(defaultId = id)

    override suspend fun clearProgress() {
        statsDao.clear()
    }
}

fun CountryStats.toStats(): Stats =
    Stats(this.countryId, this.progress)

fun Stats?.toCountryStats(defaultId: String): CountryStats =
    if (this != null) {
        CountryStats(this.id, this.percentage)
    } else {
        CountryStats(defaultId, 0f)
    }
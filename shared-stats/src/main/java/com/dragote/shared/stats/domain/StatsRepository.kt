package com.dragote.shared.stats.domain

import com.dragote.shared.stats.domain.entity.CountryStats

interface StatsRepository {

    suspend fun setStats(countryStats: CountryStats)

    suspend fun getStats(id: String): CountryStats

    suspend fun clearProgress()
}
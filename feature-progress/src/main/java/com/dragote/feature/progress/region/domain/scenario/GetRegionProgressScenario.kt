package com.dragote.feature.progress.region.domain.scenario

import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import javax.inject.Inject

class GetRegionProgressScenario @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryStatsUseCase: GetCountryStatsUseCase,
) {

    suspend operator fun invoke(region: Region): Float {
        val countryList = getCountriesUseCase(region)

        if (countryList.isEmpty()) throw Exception("Country list can't be empty")

        val stats = countryList.sumOf { countryInfo ->
            getCountryStatsUseCase(countryInfo.id).progress.toDouble()
        } / countryList.size

        return stats.toFloat()
    }
}
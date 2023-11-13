package com.dragote.feature.train.training.domain.scenario

import com.dragote.feature.train.training.domain.entity.Answers
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import com.dragote.shared.country.domain.usecase.GetCountriesUseCase
import com.dragote.shared.stats.domain.usecase.GetCountryStatsUseCase
import javax.inject.Inject

class GetAnswersScenario @Inject constructor(
    private val getCountryStatsUseCase: GetCountryStatsUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
) {

    companion object {
        const val WRONG_ANSWERS_NUMBER = 3
        const val PROGRESS_COMPLETE = 1f
    }

    suspend operator fun invoke(regions: Array<Region>, completedCountries: List<Country>): Answers {
        val correctCountry = getCorrectCountry(regions, completedCountries)
        val wrongCountries = mutableListOf<Country>()

        repeat(WRONG_ANSWERS_NUMBER) {
            wrongCountries.add(
                getFilteredCountries(regions = regions, exceptOf = wrongCountries + correctCountry).random()
            )
        }

        return Answers(
            correctCountry = correctCountry,
            options = (wrongCountries + correctCountry).shuffled()
        )
    }

    private suspend fun getCorrectCountry(regions: Array<Region>, completedCountries: List<Country>): Country {
        val uncompletedCountries = getFilteredCountries(regions, completedCountries).filter {
            getCountryStatsUseCase(it.id).progress < PROGRESS_COMPLETE
        }

        return when {
            uncompletedCountries.isNotEmpty() -> uncompletedCountries.random()
            else -> getCountries(regions).random()
        }
    }

    private fun getFilteredCountries(regions: Array<Region>, exceptOf: List<Country>): List<Country> =
        getCountries(regions).filter { countryInfo ->
            !exceptOf.contains(countryInfo)
        }

    private fun getCountries(regions: Array<Region>) = regions.flatMap { region ->
        getCountriesUseCase(region)
    }
}
package com.dragote.shared.country.domain.usecase

import com.dragote.shared.country.domain.CountriesRepository
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
) {

    operator fun invoke(region: Region): List<Country> =
        countriesRepository.get(region)
}
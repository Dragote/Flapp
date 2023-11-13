package com.dragote.shared.country.data

import com.dragote.shared.country.domain.CountriesRepository
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor() : CountriesRepository {

    override fun get(region: Region): List<Country> =
        when (region) {
            Region.EUROPE -> europeanCountries
            Region.ASIA -> asianCountries
            Region.SOUTH_AMERICA -> southAmericanCountries
            Region.NORTH_AMERICA -> northAmericanCountries
            Region.AFRICA -> africanCountries
            Region.OCEANIA -> oceanicCountries
        }
}
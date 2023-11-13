package com.dragote.shared.country.domain

import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region

interface CountriesRepository {

    fun get(region: Region): List<Country>
}
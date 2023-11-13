package com.dragote.feature.train.training.domain.entity

import com.dragote.shared.country.domain.entity.Country

data class Answers(
    val correctCountry : Country,
    val options: List<Country>
)
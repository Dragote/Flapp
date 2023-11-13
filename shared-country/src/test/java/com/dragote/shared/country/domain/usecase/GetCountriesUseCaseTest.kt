package com.dragote.shared.country.domain.usecase

import com.dragote.shared.country.domain.CountriesRepository
import com.dragote.shared.country.domain.entity.Country
import com.dragote.shared.country.domain.entity.Region
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetCountriesUseCaseTest {

    private val region = Region.EUROPE
    private val country = Country(
        id = "1",
        name = "Country",
        capital = "Capital",
        drawableRes = 1,
    )

    private val repository = mock<CountriesRepository> {
        on { get(region) } doReturn listOf(country)
    }
    private val useCase = GetCountriesUseCase(repository)

    @Test
    fun `invoke use case EXPEXT get countries`() {
        val expect = listOf(country)
        val actual = useCase.invoke(region)

        assertEquals(expect, actual)
    }
}
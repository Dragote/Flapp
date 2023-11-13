package com.dragote.flapp.di

import com.dragote.shared.country.data.CountriesRepositoryImpl
import com.dragote.shared.country.domain.CountriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CountriesModule {

    @Binds
    abstract fun bindRepository(repository: CountriesRepositoryImpl): CountriesRepository
}
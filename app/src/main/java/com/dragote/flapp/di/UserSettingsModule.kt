package com.dragote.flapp.di

import com.dragote.shared.settings.UserSettings
import com.dragote.shared.settings.UserSettingsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserSettingsModule {

    @Binds
    @Singleton
    abstract fun bindUserSettings(userSettingsImpl: UserSettingsImpl): UserSettings
}
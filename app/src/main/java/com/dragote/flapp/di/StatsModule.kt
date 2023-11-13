package com.dragote.flapp.di

import android.content.Context
import androidx.room.Room
import com.dragote.shared.stats.data.AppDatabase
import com.dragote.shared.stats.data.StatsDao
import com.dragote.shared.stats.data.StatsRepositoryImpl
import com.dragote.shared.stats.domain.StatsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {

    @Binds
    abstract fun bindRepository(repository: StatsRepositoryImpl): StatsRepository

    companion object {
        @Provides
        fun provideChannelDao(appDatabase: AppDatabase): StatsDao {
            return appDatabase.statsDao()
        }

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
            return Room.databaseBuilder(
                context = appContext,
                klass = AppDatabase::class.java,
                name = "RssReader"
            ).build()
        }
    }
}
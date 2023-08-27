package com.faroh.shamoandroid.core.di

import android.content.Context
import androidx.room.Room
import com.faroh.shamoandroid.core.data.source.local.room.ShamoDao
import com.faroh.shamoandroid.core.data.source.local.room.ShamoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ShamoDatabase =
        Room.databaseBuilder(
            context,
            ShamoDatabase::class.java,
            "Shamo.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideShamoDao(database: ShamoDatabase): ShamoDao = database.shamoDao()
}
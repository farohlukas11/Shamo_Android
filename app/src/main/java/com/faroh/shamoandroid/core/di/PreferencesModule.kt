package com.faroh.shamoandroid.core.di

import android.content.Context
import com.faroh.shamoandroid.core.data.source.preferences.ShamoPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): ShamoPreferences =
        ShamoPreferences(context)
}
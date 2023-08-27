package com.faroh.shamoandroid.core.di

import com.faroh.shamoandroid.core.data.ShamoRepository
import com.faroh.shamoandroid.core.domain.repository.IShamoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(shamoRepository: ShamoRepository): IShamoRepository
}
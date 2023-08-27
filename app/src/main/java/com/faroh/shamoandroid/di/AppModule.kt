package com.faroh.shamoandroid.di

import com.faroh.shamoandroid.core.domain.usecase.ShamoInteractor
import com.faroh.shamoandroid.core.domain.usecase.ShamoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideShamoUseCase(shamoInteractor: ShamoInteractor): ShamoUseCase
}
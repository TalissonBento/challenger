package com.talissonb.challenger.basicfeature.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import com.talissonb.challenger.basicfeature.presentation.RocketsNavigationFactory
import com.talissonb.challenger.basicfeature.presentation.RocketsUiState
import com.talissonb.challenger.core.navigation.NavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object RocketsViewModelModule {

    @Provides
    fun provideInitialRocketsUiState(): RocketsUiState = RocketsUiState()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface RocketsSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindRocketsNavigationFactory(factory: RocketsNavigationFactory): NavigationFactory
}

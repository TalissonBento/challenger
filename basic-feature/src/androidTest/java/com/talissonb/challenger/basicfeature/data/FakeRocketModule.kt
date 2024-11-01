package com.talissonb.challenger.basicfeature.data

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.talissonb.challenger.basicfeature.data.di.RocketModule
import com.talissonb.challenger.basicfeature.domain.usecase.GetRocketsUseCase
import com.talissonb.challenger.basicfeature.domain.usecase.RefreshRocketsUseCase
import com.talissonb.challenger.core.utils.resultOf
import kotlinx.coroutines.flow.flowOf

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RocketModule::class],
)
internal object FakeRocketModule {

    @Provides
    fun provideFakeGetRocketsUseCase(): GetRocketsUseCase = GetRocketsUseCase {
        flowOf(
            Result.success(generateTestRocketsFromDomain()),
        )
    }

    @Provides
    fun provideNoopRefreshRocketsUseCase(): RefreshRocketsUseCase = RefreshRocketsUseCase {
        resultOf { }
    }
}

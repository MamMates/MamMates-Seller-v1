package com.mammates.mammates_seller_v1.di

import android.content.Context
import com.mammates.mammates_seller_v1.data.repository.IntroRepositoryImpl
import com.mammates.mammates_seller_v1.data.source.local.IntroPreference
import com.mammates.mammates_seller_v1.data.source.local.dataStore
import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import com.mammates.mammates_seller_v1.domain.use_case.intro.GetIntroIsDoneUseCase
import com.mammates.mammates_seller_v1.domain.use_case.intro.IntroUseCases
import com.mammates.mammates_seller_v1.domain.use_case.intro.SetIntroIsDoneUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesIntroPreference(@ApplicationContext context: Context): IntroPreference {
        return IntroPreference(context.dataStore)
    }

    @Provides
    @Singleton
    fun providesIntroRepository(introPreference: IntroPreference): IntroRepository {
        return IntroRepositoryImpl(introPreference)
    }

    @Provides
    @Singleton
    fun providesIntroUseCase(introRepository: IntroRepository): IntroUseCases {
        return IntroUseCases(
            getIntroIsDoneUseCase = GetIntroIsDoneUseCase(introRepository),
            setIntroIsDoneUseCase = SetIntroIsDoneUseCase(introRepository)
        )
    }
}
package com.mammates.mammates_seller_v1.di

import android.content.Context
import com.mammates.mammates_seller_v1.common.Constants
import com.mammates.mammates_seller_v1.data.repository.AccountRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.AuthRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.FoodRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.IntroRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.MamRatesRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.OrderRepositoryImpl
import com.mammates.mammates_seller_v1.data.repository.TokenRepositoryImpl
import com.mammates.mammates_seller_v1.data.source.local.IntroPreference
import com.mammates.mammates_seller_v1.data.source.local.TokenPreference
import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.domain.repository.AccountRepository
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import com.mammates.mammates_seller_v1.domain.repository.FoodRepository
import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import com.mammates.mammates_seller_v1.domain.repository.MamRatesRepository
import com.mammates.mammates_seller_v1.domain.repository.OrderRepository
import com.mammates.mammates_seller_v1.domain.repository.TokenRepository
import com.mammates.mammates_seller_v1.domain.use_case.account.AccountUseCases
import com.mammates.mammates_seller_v1.domain.use_case.account.ChangePasswordUseCase
import com.mammates.mammates_seller_v1.domain.use_case.account.GetAccountUseCase
import com.mammates.mammates_seller_v1.domain.use_case.account.GetStoreUseCase
import com.mammates.mammates_seller_v1.domain.use_case.account.UpdateAccountUseCase
import com.mammates.mammates_seller_v1.domain.use_case.account.UpdateProfilePictureUseCase
import com.mammates.mammates_seller_v1.domain.use_case.auth.AuthLoginUseCase
import com.mammates.mammates_seller_v1.domain.use_case.auth.AuthRegisterUseCase
import com.mammates.mammates_seller_v1.domain.use_case.auth.AuthUseCases
import com.mammates.mammates_seller_v1.domain.use_case.food.AddFoodUseCase
import com.mammates.mammates_seller_v1.domain.use_case.food.DeleteFoodUseCase
import com.mammates.mammates_seller_v1.domain.use_case.food.FoodUseCases
import com.mammates.mammates_seller_v1.domain.use_case.food.GetAllFoodsUseCase
import com.mammates.mammates_seller_v1.domain.use_case.food.GetFoodDetailUseCase
import com.mammates.mammates_seller_v1.domain.use_case.food.UpdateFoodUseCase
import com.mammates.mammates_seller_v1.domain.use_case.intro.GetIntroIsDoneUseCase
import com.mammates.mammates_seller_v1.domain.use_case.intro.IntroUseCases
import com.mammates.mammates_seller_v1.domain.use_case.intro.SetIntroIsDoneUseCase
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.GetMamRatesUseCase
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.MamRatesUseCases
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.ReportMamRatesUseCase
import com.mammates.mammates_seller_v1.domain.use_case.order.ChangeOrderStatusUseCase
import com.mammates.mammates_seller_v1.domain.use_case.order.GetOrderDetailUseCase
import com.mammates.mammates_seller_v1.domain.use_case.order.GetOrderRecentUseCase
import com.mammates.mammates_seller_v1.domain.use_case.order.GetOrdersUseCase
import com.mammates.mammates_seller_v1.domain.use_case.order.OrderUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.ClearTokenUseCase
import com.mammates.mammates_seller_v1.domain.use_case.token.GetTokenUseCase
import com.mammates.mammates_seller_v1.domain.use_case.token.SetTokenUseCase
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMamMatesApi(): MamMatesApi {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .build()
            chain.proceed(requestHeader)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MamMatesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesIntroPreference(@ApplicationContext context: Context): IntroPreference {
        return IntroPreference(context.dataStore)
    }

    @Provides
    @Singleton
    fun providesTokenPreference(@ApplicationContext context: Context): TokenPreference {
        return TokenPreference(context.dataStore)
    }

    @Provides
    @Singleton
    fun providesIntroRepository(introPreference: IntroPreference): IntroRepository {
        return IntroRepositoryImpl(introPreference)
    }

    @Provides
    @Singleton
    fun providesTokenRepository(tokenPreference: TokenPreference): TokenRepository {
        return TokenRepositoryImpl(tokenPreference)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(mamMatesApi: MamMatesApi): AuthRepository {
        return AuthRepositoryImpl(mamMatesApi)
    }

    @Provides
    @Singleton
    fun providesOrderRepository(mamMatesApi: MamMatesApi): OrderRepository {
        return OrderRepositoryImpl(mamMatesApi)
    }

    @Provides
    @Singleton
    fun providesMamRatesRepository(mamMatesApi: MamMatesApi): MamRatesRepository {
        return MamRatesRepositoryImpl(mamMatesApi)
    }

    @Provides
    @Singleton
    fun providesAccountRepository(mamMatesApi: MamMatesApi): AccountRepository {
        return AccountRepositoryImpl(mamMatesApi)
    }

    @Provides
    @Singleton
    fun providesFoodRepository(mamMatesApi: MamMatesApi): FoodRepository {
        return FoodRepositoryImpl(mamMatesApi)
    }

    @Provides
    @Singleton
    fun providesOrderUseCase(orderRepository: OrderRepository): OrderUseCases {
        return OrderUseCases(
            getOrderDetailUseCase = GetOrderDetailUseCase(orderRepository),
            getOrdersUseCase = GetOrdersUseCase(orderRepository),
            getOrderRecentUseCase = GetOrderRecentUseCase(orderRepository),
            changeOrderStatusUseCase = ChangeOrderStatusUseCase(orderRepository)
        )
    }

    @Provides
    @Singleton
    fun providesMamRatesUseCase(mamRatesRepository: MamRatesRepository): MamRatesUseCases {
        return MamRatesUseCases(
            getMamRatesUseCase = GetMamRatesUseCase(mamRatesRepository),
            reportMamRatesUseCase = ReportMamRatesUseCase(mamRatesRepository)
        )
    }

    @Provides
    @Singleton
    fun providesFoodUseCase(foodRepository: FoodRepository): FoodUseCases {
        return FoodUseCases(
            addFoodUseCase = AddFoodUseCase(foodRepository),
            deleteFoodUseCase = DeleteFoodUseCase(foodRepository),
            getAllFoodsUseCase = GetAllFoodsUseCase(foodRepository),
            getFoodDetailUseCase = GetFoodDetailUseCase(foodRepository),
            updateFoodUseCase = UpdateFoodUseCase(foodRepository)
        )
    }

    @Provides
    @Singleton
    fun providesAccountUseCase(accountRepository: AccountRepository): AccountUseCases {
        return AccountUseCases(
            getAccountUseCase = GetAccountUseCase(accountRepository),
            getStoreUseCase = GetStoreUseCase(accountRepository),
            updateAccountUseCase = UpdateAccountUseCase(accountRepository),
            updateProfilePictureUseCase = UpdateProfilePictureUseCase(accountRepository),
            changePasswordUseCase = ChangePasswordUseCase(accountRepository)
        )
    }

    @Provides
    @Singleton
    fun providesIntroUseCase(introRepository: IntroRepository): IntroUseCases {
        return IntroUseCases(
            getIntroIsDoneUseCase = GetIntroIsDoneUseCase(introRepository),
            setIntroIsDoneUseCase = SetIntroIsDoneUseCase(introRepository)
        )
    }

    @Provides
    @Singleton
    fun providesTokenUseCases(tokenRepository: TokenRepository): TokenUseCases {
        return TokenUseCases(
            getTokenUseCase = GetTokenUseCase(tokenRepository),
            setTokenUseCase = SetTokenUseCase(tokenRepository),
            clearTokenUseCase = ClearTokenUseCase(tokenRepository)
        )
    }

    @Provides
    @Singleton
    fun providesAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            authLoginUseCase = AuthLoginUseCase(authRepository),
            authRegisterUseCase = AuthRegisterUseCase(authRepository)
        )
    }
}
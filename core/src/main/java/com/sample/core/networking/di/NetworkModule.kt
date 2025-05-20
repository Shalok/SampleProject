package com.sample.core.networking.di

import com.sample.core.BuildConfig
import com.sample.core.networking.network.contract.ApiExecutor
import com.sample.core.networking.network.impl.ApiExecutorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        factory: Factory
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    fun provideFactory(): Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

}
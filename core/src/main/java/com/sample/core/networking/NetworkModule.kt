package com.sample.core.networking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        @BaseURL baseUrl: String,
        factory: Factory
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(baseUrl)
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

    @Provides
    @BaseURL
    fun provideAPIExecutor(): String {
        return "https://dummyjson.com/"
    }

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Qualifier
    annotation class BaseURL

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention
    @Qualifier
    annotation class MainDispatcher
}
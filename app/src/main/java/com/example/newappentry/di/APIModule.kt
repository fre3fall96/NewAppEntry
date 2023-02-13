package com.example.newappentry.di

import com.example.newappentry.network.NewsApiService
import com.example.newappentry.network.repository.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    private const val BASEURL = "https://newsapi.org/v2/"

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
        .baseUrl(BASEURL)
        .build()

    @Singleton
    @Provides
    fun providesAPIService(retrofit: Retrofit) : NewsApiService = retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(newsApiService: NewsApiService) = Repository(newsApiService)
}
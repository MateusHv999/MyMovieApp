package com.example.mymovieapp.di

import com.example.mymovieapp.api.Credentials
import com.example.mymovieapp.api.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
             Retrofit.Builder()
            .baseUrl(Credentials.baseurl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(com.example.mymovieapp.api.MovieService::class.java)
}
package com.example.toycalorie.di

import com.example.toycalorie.network.CalorieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideSheetApi(): CalorieApi {
        return Retrofit.Builder()
            .baseUrl(SHEET_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CalorieApi::class.java)
    }

    private const val SHEET_URL = "https://sheetdb.io/"
}
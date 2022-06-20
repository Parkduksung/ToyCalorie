package com.example.toycalorie.di


import com.example.toycalorie.data.repo.CalorieRepository
import com.example.toycalorie.data.repo.CalorieRepositoryImpl
import com.example.toycalorie.data.source.local.CalorieLocalDataSource
import com.example.toycalorie.data.source.local.CalorieLocalDataSourceImpl
import com.example.toycalorie.data.source.remote.CalorieRemoteDataSource
import com.example.toycalorie.data.source.remote.CalorieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCalorieRepository(calorieRepositoryImpl: CalorieRepositoryImpl): CalorieRepository

    @Singleton
    @Binds
    abstract fun bindCalorieRemoteDataSource(calorieRemoteDataSourceImpl: CalorieRemoteDataSourceImpl): CalorieRemoteDataSource


    @Singleton
    @Binds
    abstract fun bindCalorieLocalDataSource(calorieLocalDataSourceImpl: CalorieLocalDataSourceImpl): CalorieLocalDataSource

}


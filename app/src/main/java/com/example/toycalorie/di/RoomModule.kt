package com.example.toycalorie.di

import android.content.Context
import androidx.room.Room
import com.example.toycalorie.room.dao.CalorieDao
import com.example.toycalorie.room.database.CalorieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideCalorieDao(calorieDatabase: CalorieDatabase): CalorieDao {
        return calorieDatabase.calorieDao()
    }

    @Singleton
    @Provides
    fun provideCalorie(@ApplicationContext appContext: Context): CalorieDatabase {
        return Room.databaseBuilder(
            appContext,
            CalorieDatabase::class.java,
            "calorie_table"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
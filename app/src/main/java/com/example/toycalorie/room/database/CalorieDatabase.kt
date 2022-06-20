package com.example.toycalorie.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.toycalorie.room.dao.CalorieDao
import com.example.toycalorie.room.entity.CalorieEntity

@Database(entities = [CalorieEntity::class], version = 1)
abstract class CalorieDatabase : RoomDatabase() {
    abstract fun calorieDao(): CalorieDao
}
package com.example.toycalorie.data.source.local

import com.example.toycalorie.room.entity.CalorieEntity
import com.example.toycalorie.util.Result

interface CalorieLocalDataSource {

    suspend fun getLocalCalorieList(): Result<List<CalorieEntity>>

    suspend fun registerCalorieEntityList(entityList: List<CalorieEntity>): Boolean

    suspend fun getLocalCalorieList(type: String): Result<List<CalorieEntity>>
}
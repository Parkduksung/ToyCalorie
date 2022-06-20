package com.example.toycalorie.data.repo

import com.example.toycalorie.data.source.local.CalorieLocalDataSource
import com.example.toycalorie.data.source.remote.CalorieRemoteDataSource
import com.example.toycalorie.network.CalorieResponse
import com.example.toycalorie.room.entity.CalorieEntity
import com.example.toycalorie.util.Result
import javax.inject.Inject

class CalorieRepositoryImpl @Inject constructor(
    private val calorieRemoteDataSource: CalorieRemoteDataSource,
    private val calorieLocalDataSource: CalorieLocalDataSource
) : CalorieRepository {

    override suspend fun getRemoteCalorieList(): Result<List<CalorieResponse>> =
        calorieRemoteDataSource.getRemoteCalorieList()

    override suspend fun getLocalCalorieList(): Result<List<CalorieEntity>> =
        calorieLocalDataSource.getLocalCalorieList()

    override suspend fun registerCalorieEntityList(entityList: List<CalorieEntity>): Boolean =
        calorieLocalDataSource.registerCalorieEntityList(entityList)
}
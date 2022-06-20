package com.example.toycalorie.data.source.remote

import com.example.toycalorie.network.CalorieApi
import com.example.toycalorie.network.CalorieResponse
import com.example.toycalorie.util.Result
import javax.inject.Inject

class CalorieRemoteDataSourceImpl @Inject constructor(private val calorieApi: CalorieApi) :
    CalorieRemoteDataSource {

    override suspend fun getRemoteCalorieList(): Result<List<CalorieResponse>> {
        return try {
            val result = calorieApi.getCalorieList().execute().body()!!
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
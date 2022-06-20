package com.example.toycalorie.data.source.remote

import com.example.toycalorie.network.CalorieResponse
import com.example.toycalorie.util.Result

interface CalorieRemoteDataSource {

    suspend fun getRemoteCalorieList(): Result<List<CalorieResponse>>
}
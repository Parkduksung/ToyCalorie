package com.example.toycalorie.data.repo

import com.example.toycalorie.data.source.local.CalorieLocalDataSource
import com.example.toycalorie.data.source.remote.CalorieRemoteDataSource

interface CalorieRepository : CalorieLocalDataSource, CalorieRemoteDataSource
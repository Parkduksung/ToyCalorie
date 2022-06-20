package com.example.toycalorie.data.source.local

import com.example.toycalorie.room.dao.CalorieDao
import com.example.toycalorie.room.entity.CalorieEntity
import com.example.toycalorie.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalorieLocalDataSourceImpl @Inject constructor(private val calorieDao: CalorieDao) :
    CalorieLocalDataSource {

    override suspend fun getLocalCalorieList(): Result<List<CalorieEntity>> {
        return try {
            Result.Success(calorieDao.getAll())
        } catch (e: Exception) {
            Result.Error(Exception("Error getAllSSGEntity!"))
        }
    }

    override suspend fun registerCalorieEntityList(entityList: List<CalorieEntity>): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                async { registerAll(entityList) }.await()
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun getLocalCalorieList(type: String): Result<List<CalorieEntity>> {
        return try {
            Result.Success(calorieDao.getCalorieEntityByType(type))
        } catch (e: Exception) {
            Result.Error(Exception("Error getAllSSGEntity!"))
        }
    }

    private suspend fun registerAll(list: List<CalorieEntity>): Boolean {
        var isAllSave = true
        list.forEach {
            isAllSave = isAllSave.and(calorieDao.registerCalorieEntity(it) > 0)
        }
        return isAllSave
    }
}
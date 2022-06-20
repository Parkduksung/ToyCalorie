package com.example.toycalorie.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.toycalorie.room.entity.CalorieEntity

@Dao
interface CalorieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerCalorieEntity(entity: CalorieEntity): Long

    @Query("SELECT * FROM calorie_table")
    fun getAll(): List<CalorieEntity>

    @Query("SELECT * FROM calorie_table WHERE `type` = (:type) ")
    fun getCalorieEntityByType(type: String): List<CalorieEntity>

}
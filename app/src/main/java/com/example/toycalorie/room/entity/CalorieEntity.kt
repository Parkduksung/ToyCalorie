package com.example.toycalorie.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.toycalorie.data.model.CalorieItem

@Entity(tableName = "calorie_table")
data class CalorieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "type") val type: String? = "",
    @ColumnInfo(name = "kcal") val kcal: String? = "",
    @ColumnInfo(name = "carbo") val carbo: String? = "",
    @ColumnInfo(name = "protein") val protein: String? = "",
    @ColumnInfo(name = "fat") val fat: String? = "",
    @ColumnInfo(name = "saturatedFat") val saturatedFat: String? = "",
    @ColumnInfo(name = "sodium") val sodium: String? = "",
    @ColumnInfo(name = "material1") val material1: String? = "",
    @ColumnInfo(name = "material2") val material2: String? = "",
    @ColumnInfo(name = "material3") val material3: String? = ""
) {
    fun toCalorieItem(): CalorieItem =
        CalorieItem(
            name,
            type,
            kcal,
            carbo,
            protein,
            fat,
            saturatedFat,
            sodium,
            material1,
            material2,
            material3
        )
}

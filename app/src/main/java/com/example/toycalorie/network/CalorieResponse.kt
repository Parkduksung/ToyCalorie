package com.example.toycalorie.network

import android.os.Parcelable
import com.example.toycalorie.room.entity.CalorieEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalorieResponse(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("kcal")
    val kcal: String? = "",
    @SerializedName("carbo")
    val carbo: String? = "",
    @SerializedName("protein")
    val protein: String? = "",
    @SerializedName("fat")
    val fat: String? = "",
    @SerializedName("saturated_fat")
    val saturatedFat: String? = "",
    @SerializedName("sodium")
    val sodium: String? = "",
    @SerializedName("material1")
    val material1: String? = "",
    @SerializedName("material2")
    val material2: String? = "",
    @SerializedName("material3")
    val material3: String? = "",
) : Parcelable {

    fun toCalorieEntity(): CalorieEntity =
        CalorieEntity(
            id = 0, name, type, kcal, carbo, protein, fat,
            saturatedFat, sodium, material1, material2, material3
        )
}

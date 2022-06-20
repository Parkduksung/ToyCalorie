package com.example.toycalorie.network

import retrofit2.Call
import retrofit2.http.GET

interface CalorieApi {

    companion object {
        private const val URL = "api/v1/3esrcl2hh030u"

    }

    @GET(URL)
    fun getCalorieList(): Call<List<CalorieResponse>>

}
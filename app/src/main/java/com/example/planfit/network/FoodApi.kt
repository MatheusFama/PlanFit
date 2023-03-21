package com.example.planfit.network

import com.example.planfit.model.FoodResponse
import com.example.planfit.util.Constants.Companion.API_FOOD_KEY
import com.example.planfit.util.Constants.Companion.FOOD_API_HOST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FoodApi {
    @GET("/parser")
    suspend fun getFoods(
        @Header("X-RapidAPI-Key")
        Key: String = API_FOOD_KEY,
        @Header("X-RapidAPI-Host")
        Host: String = FOOD_API_HOST,
        @Query("ingr")
        Ingredient: String?
    ): Response<FoodResponse>
}
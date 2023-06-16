package com.capstone.nutripal.api

import com.capstone.nutripal.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("auth/login")
    fun getLogin(
        @Header("Authorization") token: String,
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun postRegister(
        @Header("Authorization") token: String,
        @Body registerRequest: RegisterRequest
    ): Call<DefaultResponse>

    @GET("food/search")
    suspend fun getSearchFoodList(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("page") page: Int
    ): SearchResponse

    @GET("food/detail")
    suspend fun getFoodDetail(
        @Header("Authorization") token: String,
        @Query("foodId") foodId: String,
        @Query("servingId") servingId: String
    ): DetailResponse

    @GET("profile")
    suspend fun getProfileDetail(
        @Header("Authorization") token: String
    ): ProfileResponse

    @Headers("Content-Type: application/json")
    @PUT("profile")
    suspend fun updateProfileDetail(
        @Header("Authorization") token: String,
        @Body registerRequest: RegisterRequest
    ): ProfileResponse

    @GET("tracker/history")
    suspend fun getIntakes(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): IntakesResponse

    @GET("tracker")
    suspend fun getMainTracker(
        @Header("Authorization") token: String,
    ): TrackerResponse

    @POST("food/eaten")
    suspend fun postEatenFood(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): ReturnBodyEaten

    @DELETE("food/eaten")
    suspend fun deleteEatenFood(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): ReturnBodyEaten

    @POST("food/mealPlan")
    suspend fun addToMealPlan(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>,
    ): ReturnBodyEaten

    @DELETE("food/mealPlan")
    fun deleteFoodFromMealPlan(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): Call<DefaultResponse>

    @GET("food/recommend")
    fun getRecommendation(
        @Header("Authorization") token: String,
        @Query("mealTime") mealTime: String,
    ): Call<DefaultResponse>
}
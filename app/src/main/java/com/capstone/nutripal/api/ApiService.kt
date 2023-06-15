package com.capstone.nutripal.api

import com.capstone.nutripal.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Query("food_id") foodId: String,
        @Query("serving_id") servingId: String
    ): DetailResponse

    @GET("profile")
    suspend fun getProfileDetail(
        @Header("Authorization") token: String
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
}
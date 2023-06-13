package com.capstone.nutripal.api

import com.capstone.nutripal.model.DetailResponse
import com.capstone.nutripal.model.ProfileResponse
import com.capstone.nutripal.model.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
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
}
package com.capstone.nutripal.api

import com.capstone.nutripal.model.DefaultResponse
import com.capstone.nutripal.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @GET("auth/login")
    fun getLogin(
        @Header("Authorization") token: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("activityLevel") activityLevel: String,
        @Field("goal") goal: String,
    ): Call<DefaultResponse>
}
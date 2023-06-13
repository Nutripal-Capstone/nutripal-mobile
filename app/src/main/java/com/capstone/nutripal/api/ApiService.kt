package com.capstone.nutripal.api

import com.capstone.nutripal.model.DefaultResponse
import com.capstone.nutripal.model.LoginResponse
import com.capstone.nutripal.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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
}
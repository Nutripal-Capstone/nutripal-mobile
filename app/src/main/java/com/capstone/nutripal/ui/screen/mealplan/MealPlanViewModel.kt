package com.capstone.nutripal.ui.screen.mealplan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.DataTracker
import com.capstone.nutripal.model.DefaultResponse
import com.capstone.nutripal.ui.common.UiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealPlanViewModel : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<DataTracker>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataTracker>>
        get() = _uiState

    private val _loadingStateRecom = MutableStateFlow(false)
    val loadingStateRecom = _loadingStateRecom.asStateFlow()

    suspend fun getDayTracker(token: String) {
        val response = ApiConfig.getApiService().getMainTracker(
            "Bearer $token",
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data)
    }

    fun postEatenFood(token: String, id: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().postEatenFood(
                "Bearer $token",
                id
            )
        }
    }

    fun deleteEatenFood(token: String, id: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().deleteEatenFood(
                "Bearer $token",
                id
            )
        }
    }

    fun deleteFoodFromMealPlan(token: String, id: Int) {
        val client = ApiConfig.getApiService().deleteFoodFromMealPlan("Bearer $token", id)
        client.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        getDayTracker(token)
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<DefaultResponse>() {}.type
                    val errorResponse: DefaultResponse? = gson.fromJson(response.errorBody()?.charStream(), type)
                    println(errorResponse?.message)
                    Log.e("MealPlanViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("MealPlanViewModel", "onFailure: failed to fetch data")
            }
        })
//        viewModelScope.launch {
//            val response = ApiConfig.getApiService().deleteFoodFromMealPlan(
//                "Bearer $token",
//                id
//            )
//        }
    }

    fun getRecommendation(token: String, mealTime: String = "") {
        _loadingStateRecom.value = true
        val client = ApiConfig.getApiService().getRecommendation("Bearer $token", mealTime)
        client.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        getDayTracker(token)
                    }
                    _loadingStateRecom.value = false
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<DefaultResponse>() {}.type
                    val errorResponse: DefaultResponse? = gson.fromJson(response.errorBody()?.charStream(), type)
                    println(errorResponse?.message)
                    Log.e("MealPlanViewModel", "onFailure: ${response.message()}")
                    _loadingStateRecom.value = false
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                _loadingStateRecom.value = false
                Log.e("MealPlanViewModel", "onFailure: failed to fetch data")
            }
        })
    }
}
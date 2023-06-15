package com.capstone.nutripal.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPageViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<DataDetail>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataDetail>>
        get() = _uiState

    private val _resultDetail = MutableStateFlow<List<DataDetail>>(emptyList())
    val resultDetail = _resultDetail.asStateFlow()

    suspend fun getFoodById(token: String, foodId: String, servingId: String) {
        try {
            val response = ApiConfig.getApiService().getFoodDetail(
                "Bearer $token",
                foodId,
                servingId
            )
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(response.data as DataDetail)
        } catch (e: Exception) {
            Log.w("DetailPageViewModel", "onFailure: ${e.message}")
        }
    }

    fun addToMealPlan(token: String, item: DataDetail, mealTime: String) {
        try {
            viewModelScope.launch {
                val response = ApiConfig.getApiService().addToMealPlan(
                    "Bearer $token",
                    mapOf(
                        "foodId" to item.foodId.toString(),
                        "servingId" to item.servingId.toString(),
                        "mealTime" to mealTime
                    )
                )
                println("check errror")
                println("${response.success} ${response.message}")
            }
        } catch (e: Exception) {
            Log.w("DetailPageViewModel", "onFailure: ${e.message}")
        }
    }
}
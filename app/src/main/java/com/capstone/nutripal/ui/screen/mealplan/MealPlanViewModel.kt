package com.capstone.nutripal.ui.screen.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataTracker
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealPlanViewModel : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<DataTracker>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataTracker>>
        get() = _uiState

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
        viewModelScope.launch {
            val response = ApiConfig.getApiService().deleteFoodFromMealPlan(
                "Bearer $token",
                id
            )
        }
    }
}
package com.capstone.nutripal.ui.screen.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataDayTracker
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealPlanViewModel(repository: FakeFoodRepository) : ViewModel(){


    private val _uiState: MutableStateFlow<UiState<DataDayTracker>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataDayTracker>>
        get() = _uiState

    suspend fun getDayTracker() {
        val response = ApiConfig.getApiService().getDayTracker(
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as DataDayTracker)
    }

    fun postEatenFood(id: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().postEatenFood(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
                id
            )
        }
    }

    fun deleteEatenFood(id: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().deleteEatenFood(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
                id
            )
        }
    }

    fun deleteFoodFromMealPlan(id: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().deleteFoodFromMealPlan(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
                id
            )
        }
    }
}
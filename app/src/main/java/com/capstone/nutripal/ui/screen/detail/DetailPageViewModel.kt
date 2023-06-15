package com.capstone.nutripal.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.model.DataItem
import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Field

class DetailPageViewModel() : ViewModel() {


    private val _uiState: MutableStateFlow<UiState<DataDetail>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataDetail>>
        get() = _uiState

    private val _resultDetail = MutableStateFlow<List<DataDetail>>(emptyList())
    val resultDetail = _resultDetail.asStateFlow()

//    fun getRewardById(rewardId: String) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(repository.getOrderFakeFoodById(rewardId))
//        }
//    }

    suspend fun getFoodById(foodId: String, servingId: String) {
        val response = ApiConfig.getApiService().getFoodDetail(
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
            foodId,
            servingId
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as DataDetail)
    }

    fun addToMealPlan(item: DataDetail, mealTime: String) {
        viewModelScope.launch {
//            @Field("foodId") foodId: String,
//            @Field("servingId") servingId: String,
//            @Field("mealTime") mealTime: String,

            val response = ApiConfig.getApiService().addToMealPlan(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
                mapOf(
                    "foodId" to item.foodId.toString(),
                    "servingId" to item.servingId.toString(),
                    "mealTime" to mealTime
                )
            )
            println("check errror")
            println("${response.success} ${response.message}")
        }
    }
}
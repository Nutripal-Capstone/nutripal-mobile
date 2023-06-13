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

class DetailPageViewModel(
    private val repository: FakeFoodRepository
) : ViewModel() {


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
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IlJpY2t5IiwiZW1haWwiOiJyaWNreWFudG93bUBnbWFpbC5jb20iLCJpYXQiOjE2ODY2MzI3MTgsImV4cCI6MTY4NjcxOTExOH0.k8VDbeD6cIjh5g26tX1lhBsuKpd18gFYKw0Uxo06yjg",
            foodId,
            servingId
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as DataDetail)
//            _resultDetail.value = response.data as List<DataDetail>
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(repository.getOrderFakeFoodById(foodId))
//        val response = ApiConfig.getApiService().getSearchFoodList(
//            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IlJpY2t5YW50b3dtIiwiZW1haWwiOiJyaWNreWFudG93bUBnbWFpbC5jb20iLCJpYXQiOjE2ODYzOTk1NjksImV4cCI6MTY4ODk5MTU2OX0.apZrblUzU1Knh11PviDJN8oiGkdGi2--3WNEO7JSI2M",
//            name, 0)
//        }
    }

//    fun addToCart(reward: FakeFoodClass, count: Int) {
//        viewModelScope.launch {
//            repository.updateOrderReward(reward.id, count)
//        }
//    }
}
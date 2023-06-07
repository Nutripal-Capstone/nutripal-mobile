package com.capstone.nutripal.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailPageViewModel(
    private val repository: FakeFoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFakeFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFakeFood>>
        get() = _uiState

    fun getRewardById(rewardId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFakeFoodById(rewardId))
        }
    }

//    fun addToCart(reward: FakeFoodClass, count: Int) {
//        viewModelScope.launch {
//            repository.updateOrderReward(reward.id, count)
//        }
//    }
}
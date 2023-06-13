package com.capstone.nutripal.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FakeFoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderFakeFood>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderFakeFood>>>
        get() = _uiState

    fun getAllFoods() {
        viewModelScope.launch {
            repository.getAllFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderFoods ->
                    println(orderFoods)
                    _uiState.value = UiState.Success(orderFoods)
                }
        }
    }

}
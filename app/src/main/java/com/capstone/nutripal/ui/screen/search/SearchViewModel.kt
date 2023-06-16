package com.capstone.nutripal.ui.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.DataItem
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel(){

    private val _result = MutableStateFlow<List<DataItem>>(emptyList())
    val result = _result.asStateFlow()

    private val _uiState: MutableStateFlow<UiState<List<DataItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<DataItem>>>
        get() = _uiState

    suspend fun getSearch(token: String, name: String) {
        try {
            val response = ApiConfig.getApiService().getSearchFoodList(
                "Bearer $token", name, 0
            )
//            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(response.data as List<DataItem>)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(errorMessage = e.message.toString())
            Log.w("SearchViewModel", "onFailure: ${e.message}")
        }
    }
}
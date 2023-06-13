package com.capstone.nutripal.ui.screen.intakes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.model.IntakesDataItem
import com.capstone.nutripal.model.SearchResponse
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntakesViewModel(repository: FakeFoodRepository) : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<List<IntakesDataItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<IntakesDataItem>>>
        get() = _uiState

    suspend fun getIntakesHistory() {
        val response = ApiConfig.getApiService().getIntakesHistory(
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IlJpY2t5IiwiZW1haWwiOiJyaWNreWFudG93bUBnbWFpbC5jb20iLCJpYXQiOjE2ODY2MzI3MTgsImV4cCI6MTY4NjcxOTExOH0.k8VDbeD6cIjh5g26tX1lhBsuKpd18gFYKw0Uxo06yjg",
            0
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as List<IntakesDataItem>)

    }
}
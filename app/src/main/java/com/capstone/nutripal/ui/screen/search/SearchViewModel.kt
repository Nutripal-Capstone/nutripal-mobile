package com.capstone.nutripal.ui.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.model.DataItem
import com.capstone.nutripal.model.SearchResponse
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(repository: FakeFoodRepository) : ViewModel(){

    private val _result = MutableStateFlow<List<DataItem>>(emptyList())
    val result = _result.asStateFlow()

    private val _uiState: MutableStateFlow<UiState<List<DataItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<DataItem>>>
        get() = _uiState

    suspend fun getSearch(name: String) {
        val response = ApiConfig.getApiService().getSearchFoodList(
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IlJpY2t5IiwiZW1haWwiOiJyaWNreWFudG93bUBnbWFpbC5jb20iLCJpYXQiOjE2ODY2MzI3MTgsImV4cCI6MTY4NjcxOTExOH0.k8VDbeD6cIjh5g26tX1lhBsuKpd18gFYKw0Uxo06yjg",
            name,
            0)
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as List<DataItem>)
    }
}
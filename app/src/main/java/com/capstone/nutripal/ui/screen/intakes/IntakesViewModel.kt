package com.capstone.nutripal.ui.screen.intakes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.*
import com.capstone.nutripal.ui.common.UiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntakesViewModel() : ViewModel() {

//    private val _uiState: MutableStateFlow<UiState<IntakesResponse>> =
//        MutableStateFlow(UiState.Loading)
//    val uiState: StateFlow<UiState<IntakesResponse>>
//        get() = _uiState

    private val _result: MutableStateFlow<List<DataIntakes>?> = MutableStateFlow(null)
    val result = _result.asStateFlow()

    suspend fun getIntakes(token: String) {
        try {
            val response = ApiConfig.getApiService().getIntakes("Bearer $token", 0)
            _result.value = response.data
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(response.data as IntakesResponse)
        } catch (e: Exception) {
            Log.w("IntakesViewModel", "onFailure: ${e.message}")
        }
    }
}
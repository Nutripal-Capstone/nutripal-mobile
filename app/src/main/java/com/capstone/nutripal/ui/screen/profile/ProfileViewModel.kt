package com.capstone.nutripal.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<ProfileData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileData>>
        get() = _uiState

    suspend fun getProfileDetail(token: String) {
        try {
            val response = ApiConfig.getApiService().getProfileDetail(
                "Bearer $token")
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(response.data as ProfileData)
        } catch (e: Exception) {
            Log.w("ProfileViewModel", "onFailure: ${e.message}")
        }
    }
}
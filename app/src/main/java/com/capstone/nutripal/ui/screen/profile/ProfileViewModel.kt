package com.capstone.nutripal.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.model.SearchResponse
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(repository: FakeFoodRepository) : ViewModel(){


    private val _uiState: MutableStateFlow<UiState<ProfileData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileData>>
        get() = _uiState

    init {
    }

    suspend fun getProfileDetail() {
        val response = ApiConfig.getApiService().getProfileDetail(
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibmFtZSI6IkFkaGFtIEtoYWxpZCIsImVtYWlsIjoicmlja3lhbnRvd21AZ21haWwuY29tIiwiaWF0IjoxNjg2NzQwOTIzLCJleHAiOjE2ODkzMzI5MjN9.iXeNxh5LzM39XkHVoWmukd1rmVeBFLWX_f7TH04896A",
        )
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(response.data as ProfileData)

    }
}
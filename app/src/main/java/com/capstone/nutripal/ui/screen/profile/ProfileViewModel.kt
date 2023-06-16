package com.capstone.nutripal.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.model.RegisterRequest
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<ProfileData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileData>>
        get() = _uiState

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _height = MutableStateFlow(0)
    val height = _height.asStateFlow()

    private val _weight = MutableStateFlow(0)
    val weight = _weight.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _age = MutableStateFlow(0)
    val age = _age.asStateFlow()

    private val _activityLevel = MutableStateFlow("")
    val activityLevel = _activityLevel.asStateFlow()

    private val _goal = MutableStateFlow("")
    val goal = _goal.asStateFlow()

    private val _dietType = MutableStateFlow("")
    val dietType = _dietType.asStateFlow()

    suspend fun getProfileDetail(token: String) {
        try {
            val response = ApiConfig.getApiService().getProfileDetail(
                "Bearer $token")
            _name.value = response.data.name
            _height.value = response.data.height
            _weight.value = response.data.weight
            _gender.value = response.data.gender
            _age.value = response.data.age
            _activityLevel.value = response.data.activityLevel
            _goal.value = response.data.goal
            _dietType.value = response.data.dietType
            _uiState.value = UiState.Success(response.data)
        } catch (e: Exception) {
            Log.w("ProfileViewModel", "onFailure getProfileDetail: ${e.message}")
        }
    }

    suspend fun updateProfileDetail(
        token: String,
        name: String,
        height: String,
        weight: String,
        gender: String,
        age: String,
        activityLevel: String,
        goal: String,
        dietType: String,
    ) {
        try {
            val registerRequest = RegisterRequest(name, height.toInt(), weight.toInt(),
                gender, age.toInt(), activityLevel, goal, dietType)
            val response = ApiConfig.getApiService().updateProfileDetail("Bearer $token",
                registerRequest)

        } catch (e: Exception) {
            Log.w("ProfileViewModel", "onFailure updateProfileDetail: ${e.message}")
        }
    }
}
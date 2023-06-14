package com.capstone.nutripal.ui.screen.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.DefaultResponse
import com.capstone.nutripal.model.RegisterRequest
import com.capstone.nutripal.model.StoreDataUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val dataUser: StoreDataUser) : ViewModel() {

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _height = MutableStateFlow("")
    val height = _height.asStateFlow()

    private val _weight = MutableStateFlow("")
    val weight = _weight.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _age = MutableStateFlow("")
    val age = _age.asStateFlow()

    private val _activityLevel = MutableStateFlow("")

    private val _goal = MutableStateFlow("")
    val goal = _goal.asStateFlow()

    fun saveFirstSignupSection(
        name: String,
        age: String,
        gender: String,
    ) {
        _name.value = name
        _age.value = age
        _gender.value = gender
        println("Ini name value: ${_name.value}")
    }

    fun saveSecondSignupSection(
        height: String,
        weight: String,
        goal: String
    ) {
        _height.value = height
        _weight.value = weight
        _goal.value = goal
    }

    fun saveThirdSignupSection(
        activityLevel: String
    ) {
        _activityLevel.value = activityLevel
    }

    suspend fun signUp(
        token: String,
        dietType: String
    ) {
        val registerRequest = RegisterRequest(_name.value, _height.value.toInt(), _weight.value.toInt(),
            _gender.value, _age.value.toInt(), _activityLevel.value, _goal.value, dietType)
        val client = ApiConfig.getApiService().postRegister(token, registerRequest)
        client.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.isSuccessful) {
                    _result.value = "success"
                    viewModelScope.launch {
                        dataUser.saveJwt(response.body()?.data as String?)
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<DefaultResponse>() {}.type
                    val errorResponse: DefaultResponse? = gson.fromJson(response.errorBody()?.charStream(), type)
                    println(errorResponse?.message)
                    Log.e("LoginViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("LoginViewModel", "onFailure: failed to fetch data")
            }
        })
    }
}
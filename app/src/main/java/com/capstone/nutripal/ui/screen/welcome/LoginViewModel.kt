package com.capstone.nutripal.ui.screen.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.DefaultResponse
import com.capstone.nutripal.model.LoginResponse
import com.capstone.nutripal.model.StoreDataUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val dataUser: StoreDataUser) : ViewModel() {

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    suspend fun signIn(token: String) {
        val client = ApiConfig.getApiService().getLogin(token)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        dataUser.login(token)
                        dataUser.saveJwt(response.body()?.data?.token)
                    }
                    _result.value = "success"
                } else {
                    if (response.code() == 401) {
                        val gson = Gson()
                        val type = object : TypeToken<DefaultResponse>() {}.type
                        val errorResponse: DefaultResponse? = gson.fromJson(response.errorBody()?.charStream(), type)
                        if (errorResponse?.message == "User is not yet registered.") {
                            // navigate to form page
                            _result.value = "register"
                        } else {
                            // back to login page
                            _result.value = "fail"
                        }
                    }
                    Log.e("LoginViewModel", "onFailure: ${response.message()}")

                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println("Masuk onFailure")
                Log.e("LoginViewModel", "onFailure: failed to fetch data")
            }
        })
    }
}

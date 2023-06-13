package com.capstone.nutripal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.screen.signup.SignupViewModel
import com.capstone.nutripal.ui.screen.welcome.LoginViewModel

class ViewModelFactory(private val dataUser: StoreDataUser) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataUser) as T
        }
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(dataUser) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}
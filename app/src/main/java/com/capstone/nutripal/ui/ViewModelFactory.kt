package com.capstone.nutripal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.screen.signup.SignupViewModel
import com.capstone.nutripal.ui.screen.welcome.LoginViewModel
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.ui.screen.detail.DetailPageViewModel
import com.capstone.nutripal.ui.screen.home.HomeViewModel
import com.capstone.nutripal.ui.screen.profile.ProfileViewModel
import com.capstone.nutripal.ui.screen.search.SearchViewModel

class ViewModelFactory(private val dataUser: StoreDataUser, private val repository: FakeFoodRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataUser) as T
        } else if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(dataUser) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailPageViewModel::class.java)) {
            return DetailPageViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
package com.capstone.nutripal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.ui.screen.detail.DetailPageViewModel
import com.capstone.nutripal.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FakeFoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailPageViewModel::class.java)) {
            return DetailPageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
package com.capstone.nutripal.di

import com.capstone.nutripal.data.FakeFoodRepository


object Injection {
    fun provideRepository(): FakeFoodRepository {
        return FakeFoodRepository.getInstance()
    }
}
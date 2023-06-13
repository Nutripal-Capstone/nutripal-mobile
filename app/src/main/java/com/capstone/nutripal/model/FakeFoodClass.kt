package com.capstone.nutripal.model

data class FakeFoodClass(
    val id: String,
    val foodTitle: String,
    val photoUrl: String,
    val desc: String,
    val calorie: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val isEaten: Boolean,
    val portion : String
)
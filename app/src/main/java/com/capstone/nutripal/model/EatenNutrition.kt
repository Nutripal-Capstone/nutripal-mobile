package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class EatenNutrition(

    @field:SerializedName("protein")
    val protein: Double = 0.0,

    @field:SerializedName("fat")
    val fat: Double = 0.0,

    @field:SerializedName("calories")
    val calories: Double = 0.0,

    @field:SerializedName("carbohydrate")
    val carbohydrate: Double = 0.0
)

package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class EatenNutrition(

    @field:SerializedName("protein")
    val protein: Double,

    @field:SerializedName("fat")
    val fat: Double,

    @field:SerializedName("calories")
    val calories: Double,

    @field:SerializedName("carbohydrate")
    val carbohydrate: Double
)

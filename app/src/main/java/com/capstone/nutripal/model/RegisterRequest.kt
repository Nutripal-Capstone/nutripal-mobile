package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("activityLevel")
	val activityLevel: String,

	@field:SerializedName("goal")
	val goal: String,

	@field:SerializedName("dietType")
	val dietType: String,
)

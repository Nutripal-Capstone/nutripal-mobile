package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: ProfileData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ProfileData(

	@field:SerializedName("goal")
	val goal: String = "",

	@field:SerializedName("gender")
	val gender: String = "",

	@field:SerializedName("weight")
	val weight: Int = 0,

	@field:SerializedName("dietType")
	val dietType: String = "",

	@field:SerializedName("picture")
	val picture: String = "",

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("age")
	val age: Int = 0,

	@field:SerializedName("activityLevel")
	val activityLevel: String = "",

	@field:SerializedName("email")
	val email: String = "",

	@field:SerializedName("height")
	val height: Int = 0,
)

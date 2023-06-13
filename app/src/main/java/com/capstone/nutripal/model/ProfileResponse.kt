package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: ProfileData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ProfileData(

	@field:SerializedName("goal")
	val goal: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("dietType")
	val dietType: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("mealsPerDay")
	val mealsPerDay: Int? = null,

	@field:SerializedName("firebaseId")
	val firebaseId: String? = null,

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("activityLevel")
	val activityLevel: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

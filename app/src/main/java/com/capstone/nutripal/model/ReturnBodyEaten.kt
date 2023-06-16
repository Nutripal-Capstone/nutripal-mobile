package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName
data class ReturnBodyEaten(
	@field:SerializedName("data")
	val data: Nothing? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

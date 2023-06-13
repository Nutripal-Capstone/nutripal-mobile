package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

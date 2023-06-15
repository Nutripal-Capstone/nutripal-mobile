package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("servingId")
	val servingId: String? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("foodId")
	val foodId: String? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double? = null,

	@field:SerializedName("servingDescription")
	val servingDescription: String? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,
)

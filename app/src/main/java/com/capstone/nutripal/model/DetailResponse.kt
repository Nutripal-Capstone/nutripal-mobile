package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("data")
	val data: DataDetail? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataDetail(

	@field:SerializedName("servingId")
	val servingId: String? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("fiber")
	val fiber: Double? = null,

	@field:SerializedName("potassium")
	val potassium: Int? = null,

	@field:SerializedName("calcium")
	val calcium: Int? = null,

	@field:SerializedName("vitaminA")
	val vitaminA: Int? = null,

	@field:SerializedName("addedSugars")
	val addedSugars: Double? = null,

	@field:SerializedName("vitaminC")
	val vitaminC: Double? = null,

	@field:SerializedName("vitaminD")
	val vitaminD: Double? = null,

	@field:SerializedName("foodType")
	val foodType: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("foodId")
	val foodId: String? = null,

	@field:SerializedName("saturatedFat")
	val saturatedFat: Double? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double? = null,

	@field:SerializedName("sodium")
	val sodium: Int? = null,

	@field:SerializedName("monounsaturatedFat")
	val monounsaturatedFat: Double? = null,

	@field:SerializedName("servingDescription")
	val servingDescription: String? = null,

	@field:SerializedName("polyunsaturatedFat")
	val polyunsaturatedFat: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("transFat")
	val transFat: Double? = null,

	@field:SerializedName("cholesterol")
	val cholesterol: Int? = null,

	@field:SerializedName("iron")
	val iron: Double? = null,

	@field:SerializedName("sugar")
	val sugar: Double? = null
)

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

	@field:SerializedName("serving_id")
	val servingId: String? = null,

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("fiber")
	val fiber: Double? = null,

	@field:SerializedName("potassium")
	val potassium: Int? = null,

	@field:SerializedName("calcium")
	val calcium: Int? = null,

	@field:SerializedName("vitamin_a")
	val vitaminA: Int? = null,

	@field:SerializedName("added_sugars")
	val addedSugars: Double? = null,

	@field:SerializedName("vitamin_c")
	val vitaminC: Double? = null,

	@field:SerializedName("vitamin_d")
	val vitaminD: Double? = null,

	@field:SerializedName("food_type")
	val foodType: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("food_id")
	val foodId: String? = null,

	@field:SerializedName("saturated_fat")
	val saturatedFat: Double? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double? = null,

	@field:SerializedName("sodium")
	val sodium: Int? = null,

	@field:SerializedName("monounsaturated_fat")
	val monounsaturatedFat: Double? = null,

	@field:SerializedName("serving_description")
	val servingDescription: String? = null,

	@field:SerializedName("polyunsaturated_fat")
	val polyunsaturatedFat: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("trans_fat")
	val transFat: Double? = null,

	@field:SerializedName("cholesterol")
	val cholesterol: Int? = null,

	@field:SerializedName("iron")
	val iron: Double? = null,

	@field:SerializedName("sugar")
	val sugar: Double? = null
)

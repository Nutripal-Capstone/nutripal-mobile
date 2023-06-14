package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class IntakesResponse(

	@field:SerializedName("data")
	val data: List<DataIntakes>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class EatenFoodItem(

	@field:SerializedName("foodName")
	val foodName: String,

	@field:SerializedName("servingDescription")
	val servingDescription: String,

	@field:SerializedName("servingId")
	val servingId: String,

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("fat")
	val fat: Double,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double
)

data class DataIntakes(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("eatenFood")
	val eatenFood: List<EatenFoodItem>,

	@field:SerializedName("nutritionGoal")
	val nutritionGoal: NutritionGoal,

	@field:SerializedName("eatenNutrition")
	val eatenNutrition: EatenNutrition
)

data class NutritionGoal(

	@field:SerializedName("calorieGoal")
	val calorieGoal: Int,

	@field:SerializedName("carbohydrateGoal")
	val carbohydrateGoal: Int,

	@field:SerializedName("fatGoal")
	val fatGoal: Int,

	@field:SerializedName("proteinGoal")
	val proteinGoal: Int
)

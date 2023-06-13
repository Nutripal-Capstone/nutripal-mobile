package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class IntakesHistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NutritionGoal(

	@field:SerializedName("calorieGoal")
	val calorieGoal: Int? = null,

	@field:SerializedName("carbohydrateGoal")
	val carbohydrateGoal: Int? = null,

	@field:SerializedName("fatGoal")
	val fatGoal: Int? = null,

	@field:SerializedName("proteinGoal")
	val proteinGoal: Int? = null
)

data class IntakesDataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("eatenFood")
	val eatenFood: List<Any?>? = null,

	@field:SerializedName("nutritionGoal")
	val nutritionGoal: NutritionGoal? = null
)

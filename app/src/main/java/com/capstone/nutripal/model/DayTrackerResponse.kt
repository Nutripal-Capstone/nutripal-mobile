package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class DayTrackerResponse(

	@field:SerializedName("data")
	val data: DataDayTracker? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NutritionGoal(

	@field:SerializedName("calorieGoal")
	val calorieGoal: Double? = 0.0,

	@field:SerializedName("carbohydrateGoal")
	val carbohydrateGoal: Double? = 0.0,

	@field:SerializedName("fatGoal")
	val fatGoal: Double? = 0.0,

	@field:SerializedName("proteinGoal")
	val proteinGoal: Double? = 0.0
)


data class EatenFood(

	@field:SerializedName("lunch")
	val lunch: List<FoodItem?>? = null,

	@field:SerializedName("breakfast")
	val breakfast: List<FoodItem?>? = null,

	@field:SerializedName("dinner")
	val dinner: List<FoodItem?>? = null
)

data class MealPlan(

	@field:SerializedName("lunch")
	val lunch: List<FoodItem?>? = null,

	@field:SerializedName("breakfast")
	val breakfast: List<FoodItem?>? = null,

	@field:SerializedName("dinner")
	val dinner: List<FoodItem?>? = null
)

data class FoodItem(

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("servingDescription")
	val servingDescription: String? = null,

	@field:SerializedName("servingId")
	val servingId: String? = null,

	@field:SerializedName("foodId")
	val foodId: String? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double? = null
)


data class DataDayTracker(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("eatenFood")
	val eatenFood: EatenFood? = null,

	@field:SerializedName("nutritionGoal")
	val nutritionGoal: NutritionGoal? = null,

	@field:SerializedName("mealPlan")
	val mealPlan: MealPlan? = null
)

data class ReturnBodyEaten(
	@field:SerializedName("data")
	val data: Nothing? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

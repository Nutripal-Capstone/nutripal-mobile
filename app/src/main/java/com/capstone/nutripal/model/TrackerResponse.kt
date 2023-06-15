package com.capstone.nutripal.model

import com.google.gson.annotations.SerializedName

data class TrackerResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("eatenFood")
	val eatenFood: EatenFood,

	@field:SerializedName("wholeNutrition")
	val wholeNutrition: WholeNutrition,

	@field:SerializedName("eatenNutrition")
	val eatenNutrition: EatenNutrition,

	@field:SerializedName("nutritionGoal")
	val nutritionGoal: NutritionGoal,

	@field:SerializedName("mealPlan")
	val mealPlan: MealPlan
)

data class EatenFood(

	@field:SerializedName("lunch")
	val lunch: List<FoodItem>,

	@field:SerializedName("breakfast")
	val breakfast: List<FoodItem>,

	@field:SerializedName("dinner")
	val dinner: List<FoodItem>
)

data class WholeNutrition(

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("fat")
	val fat: Double,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double
)

data class FoodItem(

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

	@field:SerializedName("id")
	val id: Double,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Double,
)

data class MealPlan(

	@field:SerializedName("lunch")
	val lunch: List<FoodItem>,

	@field:SerializedName("breakfast")
	val breakfast: List<FoodItem>,

	@field:SerializedName("dinner")
	val dinner: List<FoodItem>
)

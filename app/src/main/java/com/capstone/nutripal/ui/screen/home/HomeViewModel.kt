package com.capstone.nutripal.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.model.*
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<String>>
        get() = _uiState

    private val _resultEatenFoods: MutableStateFlow<MutableList<FoodItem>?> = MutableStateFlow(null)
    val resultEatenFoods = _resultEatenFoods.asStateFlow()

    private val _resultMealPlans: MutableStateFlow<MutableList<FoodItem>?> = MutableStateFlow(null)
    val resultMealPlans = _resultMealPlans.asStateFlow()

//    private val _resultEatenNutrition: MutableStateFlow<EatenNutrition> = MutableStateFlow(EatenNutrition())
//    val resultEatenNutrition = _resultEatenNutrition.asStateFlow()

    private val _resultNutritionGoal: MutableStateFlow<NutritionGoal> = MutableStateFlow(NutritionGoal())
    val resultNutritionGoal = _resultNutritionGoal.asStateFlow()

    private val _currentCalories = MutableStateFlow(0)
    val currentCalories = _currentCalories.asStateFlow()

    private val _currentProtein = MutableStateFlow(0)
    val currentProtein = _currentProtein.asStateFlow()

    private val _currentCarbs = MutableStateFlow(0)
    val currentCarbs = _currentCarbs.asStateFlow()

    private val _currentFat = MutableStateFlow(0)
    val currentFat = _currentFat.asStateFlow()

    suspend fun getAllFoods(token: String) {
        try {
            val response = ApiConfig.getApiService().getMainTracker("Bearer $token")

            // move the response of all eatenFoods
            val eatenFoods = mutableListOf<FoodItem>()
            val responseEatenFoods = response.data.eatenFood
            responseEatenFoods.breakfast.forEach { food ->
                eatenFoods.add(food)
            }
            responseEatenFoods.lunch.forEach { food ->
                eatenFoods.add(food)
            }
            responseEatenFoods.dinner.forEach { food ->
                eatenFoods.add(food)
            }
            _resultEatenFoods.value = eatenFoods

            // move the response of all uneatenFoods
            val mealPlans = mutableListOf<FoodItem>()
            val responseMealPlans = response.data.mealPlan
            responseMealPlans.breakfast.forEach { food ->
                mealPlans.add(food)
            }
            responseMealPlans.lunch.forEach { food ->
                mealPlans.add(food)
            }
            responseMealPlans.dinner.forEach { food ->
                mealPlans.add(food)
            }
            _resultMealPlans.value = mealPlans

//            _resultEatenNutrition.value = response.data.eatenNutrition
            _resultNutritionGoal.value = response.data.nutritionGoal

            _currentCalories.value = response.data.eatenNutrition.calories.toInt()
            _currentProtein.value = response.data.eatenNutrition.protein.toInt()
            _currentCarbs.value = response.data.eatenNutrition.carbohydrate.toInt()
            _currentFat.value = response.data.eatenNutrition.fat.toInt()

            println("UNEATEN FOODS IN VIEWMODEL")
            println(_resultMealPlans.value)

            println("EATEN FOODS IN VIEWMODEL")
            println(_resultEatenFoods.value)

        } catch (e: Exception) {
            Log.w("HomeViewModel", "onFailure: ${e.message}")
        }
    }

    fun onEaten(food: FoodItem) {

        _resultMealPlans.value = _resultMealPlans.value?.toMutableList()?.apply {
            remove(food)
        }
        _resultEatenFoods.value = _resultEatenFoods.value?.toMutableList()?.apply {
            add(food)
        }

        _currentCalories.value += food.calories.toInt()
        _currentProtein.value += food.protein.toInt()
        _currentCarbs.value += food.carbohydrate.toInt()
        _currentFat.value += food.fat.toInt()

    }

    fun onUneaten(food: FoodItem) {
        _resultMealPlans.value = _resultMealPlans.value?.toMutableList()?.apply {
            add(0, food)
        }
        _resultEatenFoods.value = _resultEatenFoods.value?.toMutableList()?.apply {
            remove(food)
        }

        _currentCalories.value -= food.calories.toInt()
        _currentProtein.value -= food.protein.toInt()
        _currentCarbs.value -= food.carbohydrate.toInt()
        _currentFat.value -= food.fat.toInt()
    }

}
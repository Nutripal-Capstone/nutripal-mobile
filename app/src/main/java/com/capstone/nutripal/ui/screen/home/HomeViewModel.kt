package com.capstone.nutripal.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.nutripal.api.ApiConfig
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.*
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: FakeFoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<String>>
        get() = _uiState

//    private val _allEatenFoods: MutableState<MutableList<FakeFoodClass>> = mutableStateOf(mutableListOf())
//    val allEatenFoods: State<List<FakeFoodClass>> = _allEatenFoods

//    private val _allEatenFoods: MutableState<MutableList<FakeFoodClass>> = mutableStateOf(FakeFoodData.mutListOfFoodsEaten)
//    val allEatenFoods: State<List<FakeFoodClass>> = _allEatenFoods
//
//    private val _allUneatenFoods: MutableState<MutableList<FakeFoodClass>> = mutableStateOf(FakeFoodData.mutListOfFoodsNotEaten)
//    val allUneatenFoods: State<List<FakeFoodClass>> = _allUneatenFoods

    private val _allEatenFoods: MutableList<FakeFoodClass> = FakeFoodData.mutListOfFoodsEaten
    val allEatenFoods: List<FakeFoodClass> = _allEatenFoods

    private val _allUneatenFoods: MutableList<FakeFoodClass> = FakeFoodData.mutListOfFoodsNotEaten
    val allUneatenFoods: List<FakeFoodClass> = _allUneatenFoods

//    fun getAllFoods() {
//        viewModelScope.launch {
//            repository.getAllEatenFoods()
//                .collect { eatenFoods ->
//                    println(eatenFoods)
//                    _allEatenFoods.value = eatenFoods.toMutableList()
//                }
//            repository.getAllUneatenFoods()
//                .collect { uneatenFoods ->
//                    println(uneatenFoods)
//                    _allEatenFoods.value = uneatenFoods.toMutableList()
//                    _uiState.value = UiState.Success("Berhasil")
//                }
//        }
//    }

//    fun onEaten(food: FakeFoodClass) {
//        _allUneatenFoods.remove(food)
//        _allEatenFoods.add(food)
//
//        println("EATEN FOODS IN VIEWMODEL")
//        println(_allEatenFoods)
//
//        println("UNEATEN FOODS IN VIEWMODEL")
//        println(_allUneatenFoods)
//    }
//
//    fun onUneaten(food: FakeFoodClass) {
//        _allUneatenFoods.add(food)
//        _allEatenFoods.remove(food)
//    }

    private val _resultEatenFoods: MutableStateFlow<MutableList<FoodItem>?> = MutableStateFlow(null)
    val resultEatenFoods = _resultEatenFoods.asStateFlow()

    private val _resultMealPlans: MutableStateFlow<MutableList<FoodItem>?> = MutableStateFlow(null)
    val resultMealPlans = _resultMealPlans.asStateFlow()

    private val _resultEatenNutrition: MutableStateFlow<EatenNutrition> = MutableStateFlow(EatenNutrition())
    val resultEatenNutrition = _resultEatenNutrition.asStateFlow()

    private val _resultNutritionGoal: MutableStateFlow<NutritionGoal> = MutableStateFlow(NutritionGoal())
    val resultNutritionGoal = _resultNutritionGoal.asStateFlow()

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

            _resultEatenNutrition.value = response.data.eatenNutrition
            _resultNutritionGoal.value = response.data.nutritionGoal

            println("UNEATEN FOODS IN VIEWMODEL")
            println(_resultMealPlans.value)

            println("EATEN FOODS IN VIEWMODEL")
            println(_resultEatenFoods.value)

//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success("SUCCESS")
        } catch (e: Exception) {
            Log.w("HomeViewModel", "onFailure: ${e.message}")
        }
    }

    fun onEaten(food: FoodItem) {
//        _resultMealPlans.value?.remove(food)
//        _resultEatenFoods.value?.add(food)

        _resultMealPlans.value = _resultMealPlans.value?.toMutableList()?.apply {
            remove(food)
        }
        _resultEatenFoods.value = _resultEatenFoods.value?.toMutableList()?.apply {
            add(food)
        }
    }

//    val newMealPlans = _resultMealPlans.value
//    newMealPlans?.remove(food)
//
//    _resultMealPlans.value = newMealPlans
//
//    val newEatenFoods = _resultEatenFoods.value
//    newEatenFoods?.add(food)
//
//    _resultEatenFoods.value = newEatenFoods
//
//    println("UNEATEN FOODS IN VIEWMODEL ON SWIPE")
//    println(_resultMealPlans.value)
//
//    println("EATEN FOODS IN VIEWMODEL ON SWIPE")
//    println(_resultEatenFoods.value)

    fun onUneaten(food: FoodItem) {
//        _resultMealPlans.value?.add(food)
//        _resultEatenFoods.value?.remove(food)
        _resultMealPlans.value = _resultMealPlans.value?.toMutableList()?.apply {
            add(food)
        }
        _resultEatenFoods.value = _resultEatenFoods.value?.toMutableList()?.apply {
            remove(food)
        }
    }

}
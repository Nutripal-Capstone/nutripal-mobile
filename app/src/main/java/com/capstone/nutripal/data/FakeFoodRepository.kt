package com.capstone.nutripal.data

import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.FakeFoodData
import com.capstone.nutripal.model.OrderFakeFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeFoodRepository {

    private val orderFakeFood = mutableListOf<OrderFakeFood>()
    private val fakeEatenFood = mutableListOf<FakeFoodClass>()
    private val fakeUneatenFood = mutableListOf<FakeFoodClass>()

    init {
        if (orderFakeFood.isEmpty()) {
            FakeFoodData.listOfFoods.forEach {
                orderFakeFood.add(OrderFakeFood(it, 0))
            }
        }
        if (fakeEatenFood.isEmpty()) {
            FakeFoodData.listOfFoodsEaten.forEach {
                fakeEatenFood.add(it)
            }
        }
        if (fakeUneatenFood.isEmpty()) {
            FakeFoodData.listOfFoodsNotEaten.forEach {
                fakeUneatenFood.add(it)
            }
        }
    }

    fun getAllFoods(): Flow<List<OrderFakeFood>> {
        return flowOf(orderFakeFood)
    }

    fun getAllEatenFoods(): Flow<List<FakeFoodClass>> {
        return flowOf(fakeEatenFood)
    }

    fun getAllUneatenFoods(): Flow<List<FakeFoodClass>> {
        return flowOf(fakeUneatenFood)
    }

//    fun getAddedOrderFakeFoods(): Flow<List<OrderFakeFood>> {
//        return getAllRewards()
//            .map { orderFakeFood ->
//                orderFakeFood.filter { orderReward ->
//                    orderReward.count != 0
//                }
//            }
//    }

    companion object {
        @Volatile
        private var instance: FakeFoodRepository? = null

        fun getInstance(): FakeFoodRepository =
            instance ?: synchronized(this) {
                FakeFoodRepository().apply {
                    instance = this
                }
            }
    }
}
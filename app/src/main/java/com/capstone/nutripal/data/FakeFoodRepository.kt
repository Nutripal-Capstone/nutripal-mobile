package com.capstone.nutripal.data

import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.FakeFoodData
import com.capstone.nutripal.model.OrderFakeFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeFoodRepository {

    private val orderFakeFood = mutableListOf<OrderFakeFood>()

    init {
        if (orderFakeFood.isEmpty()) {
            FakeFoodData.listOfFoods.forEach {
                orderFakeFood.add(OrderFakeFood(it, 0))
            }
        }
    }

    fun getAllFoods(): Flow<List<OrderFakeFood>> {
        return flowOf(orderFakeFood)
    }

    fun getOrderFakeFoodById(foodId: String): OrderFakeFood {
        return orderFakeFood.first {
            it.food.id == foodId
        }
    }

//    fun updateOrderFakeFood(rewardId: Long, newCountValue: Int): Flow<Boolean> {
//        val index = orderFakeFood.indexOfFirst { it.reward.id == rewardId }
//        val result = if (index >= 0) {
//            val orderReward = orderFakeFood[index]
//            orderFakeFood[index] =
//                orderReward.copy(reward = orderReward.reward, count = newCountValue)
//            true
//        } else {
//            false
//        }
//        return flowOf(result)
//    }

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
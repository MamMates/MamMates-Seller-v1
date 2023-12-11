package com.mammates.mammates_seller_v1.presentation.pages.main.store

import com.mammates.mammates_seller_v1.domain.model.FoodItem

data class StoreState(
    val isAuth: Boolean = false,
    val storeName: String = "",
    val storeAddress: String = "",

    val foods: List<FoodItem>? = null
)

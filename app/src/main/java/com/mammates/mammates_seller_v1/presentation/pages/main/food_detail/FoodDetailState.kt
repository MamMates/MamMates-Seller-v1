package com.mammates.mammates_seller_v1.presentation.pages.main.food_detail

import com.mammates.mammates_seller_v1.util.Rating

data class FoodDetailState(
    val foodName: String = "",
    val foodNameValidation: String? = null,

    val foodPrice: Int? = null,
    val foodPriceValidation: String? = null,

    val foodCategory: String? = null,
    val suggestion: String = "Consider making your dish more enticing with creative presentation or exciting flavors to boost its MamRates appeal.",

    val rating: Rating = Rating.ZERO
)

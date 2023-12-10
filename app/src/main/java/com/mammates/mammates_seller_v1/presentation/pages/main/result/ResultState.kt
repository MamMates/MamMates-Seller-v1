package com.mammates.mammates_seller_v1.presentation.pages.main.result

import com.mammates.mammates_seller_v1.util.Rating

data class ResultState(
    val rating: Rating = Rating.ZERO,
    val category: String = "",
    val price: Int = 0,
)

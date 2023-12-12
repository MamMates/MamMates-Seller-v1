package com.mammates.mammates_seller_v1.presentation.pages.main.add

import android.net.Uri
import com.mammates.mammates_seller_v1.util.Rating

data class AddState(
    val foodName: String = "",
    val foodNameValidation: String? = null,

    val foodPrice: Int = 0,
    val foodPriceValidation: String? = null,

    val foodCategory: String? = null,
    val suggestion: String = "Consider making your dish more enticing with creative presentation or exciting flavors to boost its MamRates appeal.",

    val rating: Rating = Rating.ZERO,

    val foodDisplayImage: Uri = Uri.EMPTY,
    val foodMamRatesImage: Uri = Uri.EMPTY,

    )

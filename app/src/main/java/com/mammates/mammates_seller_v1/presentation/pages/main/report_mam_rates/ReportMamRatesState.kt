package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

data class ReportMamRatesState(
    val foodName: String = "",
    val foodNameValidation: String? = null,

    val foodPrice: Int? = null,
    val foodPriceValidation: String? = null,

    val rating: Int = 0,
    val ratingValidation: String? = null

)

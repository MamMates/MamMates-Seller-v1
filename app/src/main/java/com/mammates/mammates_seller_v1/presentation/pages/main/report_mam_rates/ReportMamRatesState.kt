package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import android.net.Uri

data class ReportMamRatesState(
    val foodName: String = "",
    val foodNameValidation: String? = null,

    val foodPrice: Int = 0,
    val foodPriceValidation: String? = null,

    val rating: Int = 0,
    val ratingValidation: String? = null,

    val foodImage: Uri = Uri.EMPTY,
    val foodImageValidation: String? = null,
    val token: String = "",

    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isNotAuthorizeDialogOpen: Boolean = false,

    val isLoading: Boolean = false,

    )

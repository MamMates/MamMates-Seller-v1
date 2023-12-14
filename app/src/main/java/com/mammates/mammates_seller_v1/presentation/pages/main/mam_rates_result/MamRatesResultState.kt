package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result

import android.net.Uri
import com.mammates.mammates_seller_v1.util.Rating

data class MamRatesResultState(
    val rating: Rating = Rating.Undefine,
    val category: String = "No Category Provides",
    val price: Int = -69,
    val imageUri: Uri = Uri.EMPTY,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val token: String = "",
    val isNotAuthorizeDialogOpen: Boolean = false,

    )

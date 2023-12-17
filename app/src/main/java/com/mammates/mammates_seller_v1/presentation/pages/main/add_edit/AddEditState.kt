package com.mammates.mammates_seller_v1.presentation.pages.main.add_edit

import android.net.Uri
import com.mammates.mammates_seller_v1.util.Rating

data class AddEditState(

    val id: Int = -69,
    val isEdit: Boolean = false,
    val token: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,

    val foodName: String = "",
    val foodNameValidation: String? = null,

    val foodPrice: Int = 0,
    val foodPriceValidation: String? = null,
    val foodPriceSuggestion: Int? = null,

    val foodCategory: Int? = null,
    val suggestion: String = "Consider making your dish more enticing with creative presentation or exciting flavors to boost its MamRates appeal.",

    val rating: Rating = Rating.Undefine,

    val foodDisplayImage: Uri = Uri.EMPTY,
    val foodDisplayUrlImage: String? = null,

    val foodMamRatesImage: Uri = Uri.EMPTY,
    val foodMamRatesImageValidation: String? = null,
    val foodMamRatesUrlImage: String? = null,

    val isConfirmDeleteDialogOpen: Boolean = false,
    val isNotAuthorizeDialogOpen: Boolean = false,


    )

package com.mammates.mammates_seller_v1.presentation.pages.main.store

import com.mammates.mammates_seller_v1.domain.model.FoodItem

data class StoreState(
    val token: String = "",
    val storeName: String = "No Data Store Name",
    val storeAddress: String = "No Data Store Address",

    val foods: List<FoodItem>? = null,

    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isNotAuthorizeDialogOpen: Boolean = false,
    val isRefresh: Boolean = false


)

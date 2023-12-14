package com.mammates.mammates_seller_v1.presentation.pages.main.account

data class AccountState(
    val storeName: String = "No Data Store Name",
    val token: String = "",
    val isLoading: Boolean = false,
    val storeImage: String? = null,
    val errorMessage: String? = null,
    val isNotAuthorizeDialogOpen: Boolean = false,

    )

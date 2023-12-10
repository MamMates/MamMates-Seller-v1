package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

data class AccountSettingState(
    val storeName: String = "",
    val storeNameValidation: String? = null,

    val storeAddress: String = "",
    val storeAddressValidation: String? = null,

    val name: String = "",
    val nameValidation: String? = null,

    val email: String = "",
    val emailValidation: String? = null,

    )

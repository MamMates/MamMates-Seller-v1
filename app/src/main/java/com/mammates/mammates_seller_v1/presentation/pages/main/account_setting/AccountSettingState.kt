package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

import android.net.Uri

data class AccountSettingState(
    val storeName: String = "",
    val storeNameValidation: String? = null,

    val storeAddress: String = "",
    val storeAddressValidation: String? = null,

    val name: String = "",
    val nameValidation: String? = null,

    val email: String = "",
    val emailValidation: String? = null,

    val profileImageUri: Uri = Uri.EMPTY,
    val profileImageUrl: String? = null,

    val token: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    val isConfirmDialogOpen: Boolean = false,
    val isNotAuthorizeDialogOpen: Boolean = false,

    )

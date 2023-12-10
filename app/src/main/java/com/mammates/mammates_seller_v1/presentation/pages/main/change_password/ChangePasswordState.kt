package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

data class ChangePasswordState(
    val oldPassword: String = "",
    val oldPasswordValidation: String? = null,

    val newPassword: String = "",
    val newPasswordValidation: String? = null,

    val newPasswordConfirm: String = "",
    val newPasswordConfirmValidation: String? = null
)
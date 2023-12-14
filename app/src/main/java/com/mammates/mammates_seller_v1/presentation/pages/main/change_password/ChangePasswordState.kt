package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

data class ChangePasswordState(
    val oldPassword: String = "",
    val oldPasswordValidation: String? = null,
    val oldPasswordVisible: Boolean = false,

    val newPassword: String = "",
    val newPasswordValidation: String? = null,
    val newPasswordVisible: Boolean = false,

    val newPasswordConfirm: String = "",
    val newPasswordConfirmValidation: String? = null,
    val newPasswordConfirmVisible: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isConfirmDialogOpen: Boolean = false,
    val isLoading: Boolean = false,
    val isNotAuthorizeDialogOpen: Boolean = false,

    val token: String = ""
)

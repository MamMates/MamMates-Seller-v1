package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

data class RegisterFormState(
    val storeName: String = "",
    val storeNameValidationResult: String? = null,

    val address: String = "",
    val addressValidationResult: String? = null,

    val name: String = "",
    val nameValidationResult: String? = null,

    val email: String = "",
    val emailValidationResult: String? = null,

    val password: String = "",
    val passwordValidationResult: String? = null,

    val passwordConfirm: String = "",
    val passwordConfirmValidationResult: String? = null,

    val isPasswordVisible: Boolean = false,
    val isPasswordConfirmVisible: Boolean = false,
)

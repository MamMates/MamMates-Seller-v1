package com.mammates.mammates_seller_v1.presentation.pages.auth.forgot_password

data class ResetPasswordState(
    val email: String = "",
    val emailValidationResult: String? = null
)
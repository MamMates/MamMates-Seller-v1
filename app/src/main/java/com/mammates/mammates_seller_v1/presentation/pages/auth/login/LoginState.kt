package com.mammates.mammates_seller_v1.presentation.pages.auth.login

data class LoginState(
    val email: String = "",
    val emailValidationResult: String? = null,
    val password: String = "",
    val passwordValidationResult: String? = null,
    val isPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,
    val isAuth: Boolean = false,
    val errorMessage: String? = null

)
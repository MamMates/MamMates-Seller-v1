package com.mammates.mammates_seller_v1.presentation.pages.auth.login

sealed class LoginEvent {
    data class OnChangeEmail(val email: String) : LoginEvent()
    data class OnChangePassword(val password: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()

}
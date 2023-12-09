package com.mammates.mammates_seller_v1.presentation.pages.auth.forgot_password

sealed class ResetPasswordEvent {
    data class OnChangeEmail(val email: String) : ResetPasswordEvent()
}
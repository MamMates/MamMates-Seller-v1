package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

sealed class RegisterFormEvent {
    data class OnChangeStoreName(val storeName: String) : RegisterFormEvent()
    data class OnChangeStoreAddress(val address: String) : RegisterFormEvent()
    data class OnChangeName(val name: String) : RegisterFormEvent()
    data class OnChangeEmail(val email: String) : RegisterFormEvent()
    data class OnChangePassword(val password: String) : RegisterFormEvent()
    data class OnChangeConfirmPassword(val confirmPassword: String) : RegisterFormEvent()
    data object OnTogglePasswordVisibility : RegisterFormEvent()
    data object OnTogglePasswordConfirmVisibility : RegisterFormEvent()
    data object OnRegister : RegisterFormEvent()

    data object OnDismisDialogError : RegisterFormEvent()
    data object OnDismisDialogSuccess : RegisterFormEvent()
}
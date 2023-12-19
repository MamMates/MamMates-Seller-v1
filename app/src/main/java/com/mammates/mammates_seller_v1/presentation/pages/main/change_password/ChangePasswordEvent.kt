package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

sealed class ChangePasswordEvent {
    data class OnChangeOldPassword(val password: String) : ChangePasswordEvent()
    data class OnChangeNewPassword(val password: String) : ChangePasswordEvent()
    data class OnChangeNewPasswordConfirm(val password: String) : ChangePasswordEvent()
    data object OnToggleOldPasswordVisible : ChangePasswordEvent()
    data object OnToggleNewPasswordVisible : ChangePasswordEvent()
    data object OnToggleNewPasswordConfirmVisible : ChangePasswordEvent()
    data object OnDismissDialog : ChangePasswordEvent()
    data object OnOpenConfirmDialog : ChangePasswordEvent()
    data object ClearToken : ChangePasswordEvent()
    data object OnSubmitChangePassword : ChangePasswordEvent()
}
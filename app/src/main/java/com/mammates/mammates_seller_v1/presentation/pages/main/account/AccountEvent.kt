package com.mammates.mammates_seller_v1.presentation.pages.main.account

sealed class AccountEvent {
    data object ClearToken : AccountEvent()
    data object OnDismissDialog : AccountEvent()
    data object OnRefreshPage : AccountEvent()
    data object OnOpenConfirmDialogLogout : AccountEvent()
}
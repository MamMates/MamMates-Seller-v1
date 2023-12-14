package com.mammates.mammates_seller_v1.presentation.pages.main.account

sealed class AccountEvent {
    data object OnLogout : AccountEvent()
    data object OnDismissDialog : AccountEvent()
    data object OnRefreshPage : AccountEvent()
}
package com.mammates.mammates_seller_v1.presentation.pages.main.home

sealed class HomeEvent {
    data object OnDismissErrorDialog : HomeEvent()
    data object OnRefreshPage : HomeEvent()
    data object ClearToken : HomeEvent()
}

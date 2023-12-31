package com.mammates.mammates_seller_v1.presentation.pages.main.store

sealed class StoreEvent {
    data object OnDismissDialog : StoreEvent()
    data object OnRefreshPage : StoreEvent()
    data object ClearToken : StoreEvent()
}
package com.mammates.mammates_seller_v1.presentation.pages.main.order_detail

sealed class OrderDetailEvent {
    data object OnDismissDialog : OrderDetailEvent()
    data class OnChangeStatusOrder(val isCanceled: Boolean = false) : OrderDetailEvent()
    data class OnOpenConfirmDialog(val isCanceled: Boolean = false) : OrderDetailEvent()
    data object OnDismissNotAuthorize : OrderDetailEvent()
    data object OnRefreshPage : OrderDetailEvent()
}
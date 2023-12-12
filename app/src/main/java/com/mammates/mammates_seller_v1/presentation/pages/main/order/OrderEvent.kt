package com.mammates.mammates_seller_v1.presentation.pages.main.order

import com.mammates.mammates_seller_v1.util.StatusOrder

sealed class OrderEvent {
    data class OnChangeTab(val index: Int) : OrderEvent()
    data object OnDismissErrorDialog : OrderEvent()
    data class OnOpenChangeStatusDialog(val id: Int, val statusTarget: StatusOrder) : OrderEvent()
    data object OnDismissChangeStatusDialog : OrderEvent()
    data object OnConfirmChangeStatus : OrderEvent()
}
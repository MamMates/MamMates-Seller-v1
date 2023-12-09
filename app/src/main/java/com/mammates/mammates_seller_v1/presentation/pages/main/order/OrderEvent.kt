package com.mammates.mammates_seller_v1.presentation.pages.main.order

sealed class OrderEvent {
    data class OnChangeTab(val index: Int) : OrderEvent()
}
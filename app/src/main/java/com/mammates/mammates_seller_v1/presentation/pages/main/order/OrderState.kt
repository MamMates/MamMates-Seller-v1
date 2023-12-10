package com.mammates.mammates_seller_v1.presentation.pages.main.order

import com.mammates.mammates_seller_v1.domain.model.OrderItem

data class OrderState(
    val exampleState: String = "Example Order State",
    val tabIndex: Int = 0,

    val unConfirmOrder: List<OrderItem>? = null,
    val confirmOrder: List<OrderItem>? = null,
    val finishOrder: List<OrderItem>? = null,
    val canceledOrder: List<OrderItem>? = null,
)

package com.mammates.mammates_seller_v1.presentation.pages.main.order

import com.mammates.mammates_seller_v1.domain.model.OrderItem
import com.mammates.mammates_seller_v1.util.StatusOrder

data class OrderState(
    val token: String = "",

    val tabIndex: Int = 0,
    val orders: List<OrderItem>? = null,

    val isLoading: Boolean = false,

    val errorMessage: String? = null,
    val successMessage: String? = null,

    val isConfirmDialogOpen: Boolean = false,
    val isConfirmCanceledDialogOpen: Boolean = false,

    val orderId: Int = -69,
    val statusTarget: StatusOrder = StatusOrder.Unidentified,
    val isNotAuthorizeDialogOpen: Boolean = false,
    val isRefresh: Boolean = false
)

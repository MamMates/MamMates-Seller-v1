package com.mammates.mammates_seller_v1.presentation.pages.main.order_detail

import com.mammates.mammates_seller_v1.domain.model.FoodOrderItem
import com.mammates.mammates_seller_v1.util.StatusOrder

data class OrderDetailState(
    val id: Int = -69,
    val foods: List<FoodOrderItem>? = null,
    val invoice: String = "No Data Invoice",
    val buyer: String = "No Data Buyer",
    val date: String = "No Data Date",
    val total: Int = 0,
    val status: StatusOrder = StatusOrder.Unidentified,
    val token: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isConfirmDialogOpen: Boolean = false,
    val isConfirmCanceledDialogOpen: Boolean = false
)
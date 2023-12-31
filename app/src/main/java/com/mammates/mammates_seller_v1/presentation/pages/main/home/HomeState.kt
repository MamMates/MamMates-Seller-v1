package com.mammates.mammates_seller_v1.presentation.pages.main.home

import com.mammates.mammates_seller_v1.domain.model.OrderRecentItems

data class HomeState(
    val isOnBoarding: Boolean = false,
    val token: String = "",

    val isLoading: Boolean = false,

    val errorMessage: String? = null,

    val orderList: List<OrderRecentItems>? = null,
    val isNotAuthorizeDialogOpen: Boolean = false,
    val isRefresh: Boolean = false

)
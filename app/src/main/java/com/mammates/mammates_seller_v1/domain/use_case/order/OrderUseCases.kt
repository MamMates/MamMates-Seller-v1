package com.mammates.mammates_seller_v1.domain.use_case.order

data class OrderUseCases(
    val getOrderRecentUseCase: GetOrderRecentUseCase,
    val getOrdersUseCase: GetOrdersUseCase,
    val getOrderDetailUseCase: GetOrderDetailUseCase,
    val changeOrderStatusUseCase: ChangeOrderStatusUseCase
)
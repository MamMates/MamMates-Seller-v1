package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.OrderDetail
import com.mammates.mammates_seller_v1.domain.model.Orders
import com.mammates.mammates_seller_v1.domain.model.OrdersRecent


interface OrderRepository {
    suspend fun getOrderRecent(
        token: String
    ): ResMamMates<OrdersRecent>

    suspend fun getOrders(
        token: String,
        status: Int
    ): ResMamMates<Orders>

    suspend fun getOrderDetail(
        token: String,
        id: Int
    ): ResMamMates<OrderDetail>

    suspend fun changeOrderStatus(
        token: String,
        id: Int,
        status: Int
    ): ResMamMates<String>
}
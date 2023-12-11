package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqStatusChange
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.OrderDetail
import com.mammates.mammates_seller_v1.domain.model.Orders
import com.mammates.mammates_seller_v1.domain.model.OrdersRecent
import com.mammates.mammates_seller_v1.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: MamMatesApi
) : OrderRepository {
    override suspend fun getOrderRecent(token: String): ResMamMates<OrdersRecent> {
        return api.getOrderRecent(token)
    }

    override suspend fun getOrders(token: String, status: Int): ResMamMates<Orders> {
        return api.getOrders(token, status)
    }

    override suspend fun getOrderDetail(token: String, id: Int): ResMamMates<OrderDetail> {
        return api.getOrderDetail(token, id)
    }

    override suspend fun changeOrderStatus(
        token: String,
        id: Int,
        status: Int
    ): ResMamMates<String> {
        return api.changeOrderStatus(token, id, ReqStatusChange(status))
    }

}
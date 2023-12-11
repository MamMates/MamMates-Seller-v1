package com.mammates.mammates_seller_v1.domain.use_case.order

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.model.OrderRecentItems
import com.mammates.mammates_seller_v1.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrderRecentUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        token: String
    ): Flow<Resource<List<OrderRecentItems>>> = flow {

    }
}
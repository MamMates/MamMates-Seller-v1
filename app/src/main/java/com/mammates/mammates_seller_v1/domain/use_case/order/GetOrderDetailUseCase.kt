package com.mammates.mammates_seller_v1.domain.use_case.order

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.model.OrderDetailItem
import com.mammates.mammates_seller_v1.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOrderDetailUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int
    ): Flow<Resource<OrderDetailItem>> = flow {
        try {
            emit(Resource.Loading())
            val orderDetail = orderRepository.getOrderDetail(token, id).data?.order
            orderDetail?.let {
                emit(Resource.Success(it))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
package com.mammates.mammates_seller_v1.domain.use_case.order

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChangeOrderStatusUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        token: String,
        id: Int,
        status: Int
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message = orderRepository.changeOrderStatus(token, id, status).message
            emit(Resource.Success(message))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()
            errorMessage?.let {
                val jsonObject = JSONObject(it.charStream().readText())
                emit(
                    Resource.Error(
                        jsonObject.getString("message") ?: "An unexpected error occured"
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
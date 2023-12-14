package com.mammates.mammates_seller_v1.domain.use_case.food

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(
        token: String,
        id: Int
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message = foodRepository.deleteFood(token, id).message
            emit(Resource.Success(message))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()
            errorMessage?.let {
                val jsonObject = JSONObject(it.charStream().readText())
                if (jsonObject.getInt("code") == 401) {
                    emit(Resource.Error("401"))
                } else {
                    emit(
                        Resource.Error(
                            jsonObject.getString("message") ?: "An unexpected error occured",
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
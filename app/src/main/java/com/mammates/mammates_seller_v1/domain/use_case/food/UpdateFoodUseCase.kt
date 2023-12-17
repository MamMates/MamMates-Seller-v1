package com.mammates.mammates_seller_v1.domain.use_case.food

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(
        token: String,
        id: Int,
        image: MultipartBody.Part,
        name: String,
        price: Int,
        category: Int,
        mamImage: MultipartBody.Part,
        mamRates: Int
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message = foodRepository.updateFood(
                token,
                id,
                image,
                name,
                price,
                category,
                mamImage,
                mamRates
            ).message
            emit(Resource.Success(message))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()
            errorMessage?.let {
                try {
                    val jsonObject = JSONObject(it.charStream().readText())
                    if (jsonObject.getInt("code") == 401) {
                        emit(Resource.Error("401"))
                    } else {
                        emit(
                            Resource.Error(
                                jsonObject.getString("message") ?: "An unexpected error occurred",
                            )
                        )
                    }
                } catch (e: JSONException) {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}
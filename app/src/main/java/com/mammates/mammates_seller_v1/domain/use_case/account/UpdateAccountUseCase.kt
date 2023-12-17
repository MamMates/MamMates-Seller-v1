package com.mammates.mammates_seller_v1.domain.use_case.account

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        token: String,
        seller: String,
        address: String,
        store: String,
        email: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message =
                accountRepository.updateAccount(token, seller, address, store, email).message
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
                                jsonObject.getJSONObject("data").getString("error")
                                    ?: "An unexpected error occurred",
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
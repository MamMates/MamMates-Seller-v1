package com.mammates.mammates_seller_v1.domain.use_case.account

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.model.StoreItem
import com.mammates.mammates_seller_v1.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStoreUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        token: String
    ): Flow<Resource<StoreItem>> = flow {
        try {
            emit(Resource.Loading())
            val sellerStore = accountRepository.getStore(token).data?.store
            sellerStore?.let {
                emit(Resource.Success(sellerStore))
            }
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
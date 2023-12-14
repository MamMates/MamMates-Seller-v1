package com.mammates.mammates_seller_v1.domain.use_case.account

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateProfilePictureUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        token: String,
        image: MultipartBody.Part
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message = accountRepository.updateProfilePicture(token, image).message
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
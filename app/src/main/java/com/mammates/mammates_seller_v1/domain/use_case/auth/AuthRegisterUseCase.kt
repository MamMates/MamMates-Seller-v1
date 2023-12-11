package com.mammates.mammates_seller_v1.domain.use_case.auth

import android.util.Log
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        store: String,
        address: String,
        seller: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val message = authRepository.authRegister(
                store,
                address,
                seller,
                email,
                password,
                passwordConfirm
            ).message
            Log.i("AuthRegisterUsecase", "On Try")
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
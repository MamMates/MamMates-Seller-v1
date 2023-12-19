package com.mammates.mammates_seller_v1.domain.use_case.auth

import android.util.Log
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import com.mammates.mammates_seller_v1.util.ErrorMessage
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.TokenUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.authLogin(email, password)
            if (response.isSuccessful) {
                val token = response.headers()["Authorization"].toString()
                try {
                    val roles = JSONObject(TokenUtils.decodedToken(token)).getInt("role")
                    Log.i("Auth UseCase", "Roles : $roles")
                    if (roles == 1) {
                        emit(Resource.Success(token))
                    } else {
                        emit(Resource.Error(ErrorMessage.DIFFERENT_ROLES.message))
                    }
                } catch (e: IllegalArgumentException) {
                    emit(Resource.Error(ErrorMessage.TOKEN_INVALID.message))
                } catch (e: JSONException) {
                    emit(Resource.Error(ErrorMessage.JSON_PARSE.message))
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()
            errorMessage?.let {
                try {
                    val jsonObject = JSONObject(it.charStream().readText())
                    when (jsonObject.getInt("code")) {
                        400 -> {
                            emit(Resource.Error(
                                jsonObject.getJSONObject("data")
                                    .getString("error")
                                    .takeIf { it.isNotEmpty() }
                                    ?: HttpError.BAD_REQUEST.message
                            ))
                        }

                        401 -> {
                            emit(Resource.Error(HttpError.UNAUTHORIZED.message))
                        }

                        403 -> {
                            emit(Resource.Error(HttpError.FORBIDDEN.message))
                        }

                        404 -> {
                            emit(Resource.Error(HttpError.NOT_FOUND.message))
                        }

                        409 -> {
                            emit(Resource.Error(HttpError.CONFLICT.message))
                        }

                        500 -> {
                            emit(Resource.Error(HttpError.INTERNAL_SERVER_ERROR.message))
                        }

                        else -> {
                            emit(Resource.Error(ErrorMessage.UNEXPECTED.message))
                        }
                    }
                } catch (e: JSONException) {
                    emit(Resource.Error(ErrorMessage.JSON_PARSE.message))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(ErrorMessage.NO_INTERNET.message))
        }
    }
}
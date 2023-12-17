package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqLogin
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqRegister
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import com.mammates.mammates_seller_v1.util.ErrorMessage
import com.mammates.mammates_seller_v1.util.HttpError
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    private val api: MamMatesApi
) : AuthRepository {
    override suspend fun authLogin(email: String, password: String): ResMamMates<String> {
        return suspendCoroutine { continuation ->
            val client = api.authLogin(
                reqLogin = ReqLogin(
                    email, password
                )
            )
            client.enqueue(
                object : Callback<ResMamMates<String>> {
                    override fun onResponse(
                        call: Call<ResMamMates<String>>,
                        response: Response<ResMamMates<String>>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.headers()["Authorization"].toString()
                            continuation.resume(
                                ResMamMates(
                                    response.body()?.status ?: "",
                                    response.body()?.code ?: 0,
                                    response.body()?.message ?: "",
                                    token
                                )
                            )
                            return
                        }

                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.charStream().readText())
                            when (jsonObject.getInt("code")) {
                                400 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            jsonObject.getJSONObject("data")
                                                .getString("error")
                                                .takeIf { it.isNotEmpty() }
                                                ?: HttpError.BAD_REQUEST.message,
                                            data = null
                                        )
                                    )
                                }

                                401 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            HttpError.UNAUTHORIZED.message,
                                            data = null
                                        )
                                    )
                                }

                                403 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            HttpError.FORBIDDEN.message,
                                            data = null
                                        )
                                    )
                                }

                                404 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            HttpError.NOT_FOUND.message,
                                            data = null
                                        )
                                    )
                                }

                                409 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            HttpError.CONFLICT.message,
                                            data = null
                                        )
                                    )
                                }

                                500 -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            HttpError.INTERNAL_SERVER_ERROR.message,
                                            data = null
                                        )
                                    )
                                }

                                else -> {
                                    continuation.resume(
                                        ResMamMates(
                                            jsonObject.getString("status"),
                                            jsonObject.getInt("code"),
                                            ErrorMessage.UNEXPECTED.message,
                                            data = null
                                        )
                                    )
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResMamMates<String>>, t: Throwable) {
                        if (t is IOException) {
                            continuation.resume(
                                ResMamMates(
                                    status = "",
                                    code = 0,
                                    message = ErrorMessage.NO_INTERNET.message,
                                    data = null,
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    override suspend fun authRegister(
        store: String,
        address: String,
        seller: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): ResMamMates<String> {
        return api.authRegister(
            reqRegister = ReqRegister(
                seller, password, address, store, passwordConfirm, email
            )
        )
    }


}

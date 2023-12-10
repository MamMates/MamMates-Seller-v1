package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqLogin
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqRegister
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: MamMatesApi
) : AuthRepository {
    override suspend fun authLogin(email: String, password: String): ResMamMates<String> {
        val client = api.authLogin(
            reqLogin = ReqLogin(
                email, password
            )
        )
        var resMamMates: ResMamMates<String> = ResMamMates(
            status = "",
            code = 0,
            data = "",
            message = ""
        )
        var token = ""
        client.enqueue(
            object : Callback<ResMamMates<String>> {
                override fun onResponse(
                    call: Call<ResMamMates<String>>,
                    response: Response<ResMamMates<String>>
                ) {
                    if (response.isSuccessful) {
                        token = response.headers()["Authentication"].toString()
                        return
                    }

                    resMamMates = resMamMates.copy(
                        status = response.body()?.status ?: "",
                        code = response.body()?.code ?: 0,
                        data = token,
                        message = response.body()?.message ?: ""
                    )

                }

                override fun onFailure(call: Call<ResMamMates<String>>, t: Throwable) {
                    throw t
                }
            }
        )
        return resMamMates
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

package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import retrofit2.Response

interface AuthRepository {

    suspend fun authLogin(
        email: String,
        password: String
    ): Response<ResMamMates<String>>

    suspend fun authRegister(
        store: String,
        address: String,
        seller: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): ResMamMates<String>
}
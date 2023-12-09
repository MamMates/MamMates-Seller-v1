package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates

interface AuthRepository {

    suspend fun authLogin(
        email: String,
        password: String
    ): ResMamMates<String>

    suspend fun authRegister(
        store: String,
        address: String,
        seller: String,
        email: String,
        password: String,
        passwordConfirm: String
    )
}
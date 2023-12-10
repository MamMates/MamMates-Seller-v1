package com.mammates.mammates_seller_v1.domain.repository

interface TokenRepository {
    suspend fun setToken(token: String)
    fun getToken(): String
    suspend fun clearToken()
}
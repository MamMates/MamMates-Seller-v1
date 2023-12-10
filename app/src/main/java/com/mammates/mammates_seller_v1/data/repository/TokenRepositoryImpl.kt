package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.local.TokenPreference
import com.mammates.mammates_seller_v1.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenPreference: TokenPreference,
) : TokenRepository {

    override suspend fun setToken(token: String) {
        tokenPreference.setToken(token)
    }

    override fun getToken(): String {
        return runBlocking {
            tokenPreference.getToken().first()
        }
    }

    override suspend fun clearToken() {
        tokenPreference.clearToken()
    }

}
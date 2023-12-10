package com.mammates.mammates_seller_v1.domain.use_case.token

import com.mammates.mammates_seller_v1.domain.repository.TokenRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(token: String) {
        tokenRepository.setToken(token)
    }
}
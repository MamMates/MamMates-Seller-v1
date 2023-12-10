package com.mammates.mammates_seller_v1.domain.use_case.token

import com.mammates.mammates_seller_v1.domain.repository.TokenRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): String {
        return tokenRepository.getToken()
    }
}
package com.mammates.mammates_seller_v1.domain.use_case.auth

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val res = authRepository.authLogin(email, password)
        if (res.data.isNullOrEmpty()) {
            emit(Resource.Error(message = res.message))
            return@flow
        }
        emit(Resource.Success(res.data))
    }
}
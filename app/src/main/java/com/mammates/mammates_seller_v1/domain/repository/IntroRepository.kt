package com.mammates.mammates_seller_v1.domain.repository

import kotlinx.coroutines.flow.Flow

interface IntroRepository {
    fun getIntroIsDone(): Flow<Boolean>
    suspend fun setIntroIsDone(isDone: Boolean)
}
package com.mammates.mammates_seller_v1.domain.repository

interface IntroRepository {
    fun getIntroIsDone(): Boolean
    suspend fun setIntroIsDone(isDone: Boolean)
}
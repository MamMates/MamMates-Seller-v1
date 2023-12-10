package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.local.IntroPreference
import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    private val introPreference: IntroPreference
) : IntroRepository {
    override fun getIntroIsDone(): Flow<Boolean> {
        return introPreference.getIntroIsDone()
    }

    override suspend fun setIntroIsDone(isDone: Boolean) {
        introPreference.setIntroIsDone(isDone)
    }
}
package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.local.IntroPreference
import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    private val introPreference: IntroPreference
) : IntroRepository {
    override fun getIntroIsDone(): Boolean {
        return runBlocking {
            introPreference.getIntroIsDone().first()
        }
    }

    override suspend fun setIntroIsDone(isDone: Boolean) {
        introPreference.setIntroIsDone(isDone)
    }
}
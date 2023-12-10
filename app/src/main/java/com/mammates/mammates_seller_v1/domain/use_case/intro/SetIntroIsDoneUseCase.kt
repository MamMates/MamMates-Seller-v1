package com.mammates.mammates_seller_v1.domain.use_case.intro

import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import javax.inject.Inject

class SetIntroIsDoneUseCase @Inject constructor(
    private val introRepository: IntroRepository
) {
    suspend operator fun invoke(isDone: Boolean) {
        introRepository.setIntroIsDone(isDone)
    }
}
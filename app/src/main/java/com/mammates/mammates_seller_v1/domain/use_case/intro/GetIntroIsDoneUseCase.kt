package com.mammates.mammates_seller_v1.domain.use_case.intro

import com.mammates.mammates_seller_v1.domain.repository.IntroRepository
import javax.inject.Inject

class GetIntroIsDoneUseCase @Inject constructor(
    private val introRepository: IntroRepository
) {
    operator fun invoke(): Boolean {
        return introRepository.getIntroIsDone()

    }
}
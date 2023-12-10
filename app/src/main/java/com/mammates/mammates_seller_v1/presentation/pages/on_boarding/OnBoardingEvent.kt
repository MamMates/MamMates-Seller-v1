package com.mammates.mammates_seller_v1.presentation.pages.on_boarding

sealed class OnBoardingEvent {
    data object SetIntroIsDone : OnBoardingEvent()
}
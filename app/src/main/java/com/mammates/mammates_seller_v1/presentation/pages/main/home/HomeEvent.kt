package com.mammates.mammates_seller_v1.presentation.pages.main.home

sealed class HomeEvent {
    data object CheckOnBoardingStatus : HomeEvent()
}

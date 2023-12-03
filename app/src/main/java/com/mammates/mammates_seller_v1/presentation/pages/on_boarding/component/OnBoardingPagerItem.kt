package com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component

import com.mammates.mammates_seller_v1.R

data class OnBoardingPagerItem(
    val title: String,
    val description: String,
    val drawableRes: Int
)

val onBoardingPagerItem = listOf(
    OnBoardingPagerItem(
        title = "Sell with Impact: Mitra MamMates",
        description = "Become a seller with purpose on Mitra MamMates, where your contributions go beyond transactions, actively aiding the environment by reducing food waste.",
        drawableRes = R.drawable.pager_mitra
    ),
    OnBoardingPagerItem(
        title = "Sustainable Solutions at Your Fingertips",
        description = "Mitigate food waste effortlessly on Mitra MamMates with surplus food sales, essential articles on waste reduction, and machine learning-driven food viability checks.",
        drawableRes = R.drawable.pager_features
    ),
    OnBoardingPagerItem(
        title = "MamRates: AI-Driven Food Ratings",
        description = "Discover MamRates – your key to enhanced food ratings through advanced AI. Elevate your selling game with our streamlined approach to ensuring top-tier food quality.",
        drawableRes = R.drawable.pager_rates
    ),
)


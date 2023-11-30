package com.mammates.mammates_seller_v1.presentation.util

sealed class Screen (val route : String) {
    data object HomeScreen : Screen("home")
}
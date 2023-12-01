package com.mammates.mammates_seller_v1.presentation.util

sealed class Screen (val route : String) {
    data object HomeScreen : Screen("home")
    data object OrderScreen : Screen("order")
    data object StoreScreen : Screen("store")
    data object MamRatesScreen : Screen("mam_rates")
    data object AccountScreen : Screen("account")
}
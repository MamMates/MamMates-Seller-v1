package com.mammates.mammates_seller_v1.presentation.util.navigation

sealed class NavigationRoutes(val route: String) {

    data object Introduction : NavigationRoutes("introduction")

    data object Auth : NavigationRoutes("auth") {
        data object Login : NavigationRoutes("login")
        data object Register : NavigationRoutes("register")
    }

    data object Main : NavigationRoutes("main") {
        data object Home : NavigationRoutes("home")

        data object Order : NavigationRoutes("order")
        data object Store : NavigationRoutes("store")
        data object Add : NavigationRoutes("add")
        data object MamRates : NavigationRoutes("mam_rates")
        data object Account : NavigationRoutes("account")
    }


}
package com.mammates.mammates_seller_v1.presentation.pages.main.home.component

import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

data class CardNavigationItems(
    val title: String,
    val illustration: Int,
    val route: String,
)

val cardNavigationItems = listOf(
    CardNavigationItems(
        title = "Customize Your Store",
        illustration = R.drawable.navigation_store,
        route = NavigationRoutes.Main.Store.route
    ),
    CardNavigationItems(
        title = "Find your MamRates",
        illustration = R.drawable.navigation_mamrates,
        route = NavigationRoutes.Main.MamRates.route
    ),
    CardNavigationItems(
        title = "See your order",
        illustration = R.drawable.navigation_order,
        route = NavigationRoutes.Main.Order.route
    ),
)
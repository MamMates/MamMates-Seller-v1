package com.mammates.mammates_seller_v1.presentation.component.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.Store
import androidx.compose.ui.graphics.vector.ImageVector
import com.mammates.mammates_seller_v1.presentation.util.Screen

data class BottomNavigationItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val bottomNavigationItem = listOf(
    BottomNavigationItem(
        icon = Icons.Outlined.Home,
        label = "Home",
        route = Screen.HomeScreen.route
    ),
    BottomNavigationItem(
        icon = Icons.Outlined.ShoppingCart,
        label = "Order",
        route = Screen.OrderScreen.route
    ),
    BottomNavigationItem(
        icon = Icons.Outlined.Store,
        label = "Store",
        route = Screen.StoreScreen.route
    ),
    BottomNavigationItem(
        icon = Icons.Outlined.StarRate,
        label = "MamRates",
        route = Screen.MamRatesScreen.route
    ),
    BottomNavigationItem(
        icon = Icons.Outlined.People,
        label = "Account",
        route = Screen.AccountScreen.route
    ),
)
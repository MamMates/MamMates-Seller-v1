package com.mammates.mammates_seller_v1.presentation.util.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.OnBoardingScreen
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.Main.route
    ) {

        composable(route = NavigationRoutes.Introduction.route) {
            OnBoardingScreen(
                navController = navController
            )
        }
        mainGraph(navController)
        authGraph(navController)

    }
}

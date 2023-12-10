package com.mammates.mammates_seller_v1.presentation.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.OnBoardingScreen
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.OnBoardingViewModel
import com.mammates.mammates_seller_v1.presentation.util.navigation.graph.authGraph
import com.mammates.mammates_seller_v1.presentation.util.navigation.graph.mainGraph

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
            val viewModel = hiltViewModel<OnBoardingViewModel>()
            val state by viewModel.state.collectAsState()
            OnBoardingScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        mainGraph(navController)
        authGraph(navController)

    }
}

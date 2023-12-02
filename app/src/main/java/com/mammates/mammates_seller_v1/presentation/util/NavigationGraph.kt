package com.mammates.mammates_seller_v1.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mammates.mammates_seller_v1.presentation.pages.account.AccountScreen
import com.mammates.mammates_seller_v1.presentation.pages.account.AccountViewModel
import com.mammates.mammates_seller_v1.presentation.pages.add.AddScreen
import com.mammates.mammates_seller_v1.presentation.pages.add.AddViewModel
import com.mammates.mammates_seller_v1.presentation.pages.home.HomeScreen
import com.mammates.mammates_seller_v1.presentation.pages.home.HomeViewModel
import com.mammates.mammates_seller_v1.presentation.pages.mam_rates.MamRatesScreen
import com.mammates.mammates_seller_v1.presentation.pages.mam_rates.MamRatesViewModel
import com.mammates.mammates_seller_v1.presentation.pages.order.OrderScreen
import com.mammates.mammates_seller_v1.presentation.pages.order.OrderViewModel
import com.mammates.mammates_seller_v1.presentation.pages.store.StoreScreen
import com.mammates.mammates_seller_v1.presentation.pages.store.StoreViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.OrderScreen.route) {
            val viewModel = hiltViewModel<OrderViewModel>()
            val state by viewModel.state.collectAsState()
            OrderScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.StoreScreen.route) {
            val viewModel = hiltViewModel<StoreViewModel>()
            val state by viewModel.state.collectAsState()
            StoreScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.MamRatesScreen.route) {
            val viewModel = hiltViewModel<MamRatesViewModel>()
            val state by viewModel.state.collectAsState()
            MamRatesScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.AccountScreen.route) {
            val viewModel = hiltViewModel<AccountViewModel>()
            val state by viewModel.state.collectAsState()
            AccountScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.AddScreen.route) {
            val viewModel = hiltViewModel<AddViewModel>()
            val state by viewModel.state.collectAsState()
            AddScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

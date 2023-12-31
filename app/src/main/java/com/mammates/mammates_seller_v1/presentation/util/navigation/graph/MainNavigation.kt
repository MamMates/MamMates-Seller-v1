package com.mammates.mammates_seller_v1.presentation.util.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mammates.mammates_seller_v1.presentation.pages.main.account.AccountScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.account.AccountViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.account_setting.AccountSettingScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.account_setting.AccountSettingViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.add_edit.AddEditScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.add_edit.AddEditViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.change_password.ChangePasswordScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.change_password.ChangePasswordViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.home.HomeScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.home.HomeViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates.MamRatesScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates.MamRatesViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result.MamRatesResultScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result.MamRatesResultViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.order.OrderScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.order.OrderViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.order_detail.OrderDetailScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.order_detail.OrderDetailViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates.ReportMamRatesScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates.ReportMamRatesViewModel
import com.mammates.mammates_seller_v1.presentation.pages.main.store.StoreScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.store.StoreViewModel
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        route = NavigationRoutes.Main.route,
        startDestination = NavigationRoutes.Main.Home.route,
    ) {
        composable(route = NavigationRoutes.Main.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsState()

            HomeScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = NavigationRoutes.Main.Order.route,
        ) {
            val viewModel = hiltViewModel<OrderViewModel>()
            val state by viewModel.state.collectAsState()
            OrderScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = NavigationRoutes.Main.Result.route) {
            val viewModel = hiltViewModel<MamRatesResultViewModel>()
            val state by viewModel.state.collectAsState()
            MamRatesResultScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = NavigationRoutes.Main.OrderDetail.route + "?order_id={order_id}",
            arguments = listOf(
                navArgument(
                    name = "order_id",
                ) {
                    type = NavType.IntType
                    defaultValue = -69
                }
            )
        ) {
            val viewModel = hiltViewModel<OrderDetailViewModel>()
            val state by viewModel.state.collectAsState()
            OrderDetailScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = NavigationRoutes.Main.Store.route) {
            val viewModel = hiltViewModel<StoreViewModel>()
            val state by viewModel.state.collectAsState()
            StoreScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = NavigationRoutes.Main.MamRates.route) {
            val viewModel = hiltViewModel<MamRatesViewModel>()
            val state by viewModel.state.collectAsState()
            MamRatesScreen(
                navController = navController,
                state = state,
            )
        }
        composable(route = NavigationRoutes.Main.Account.route) {
            val viewModel = hiltViewModel<AccountViewModel>()
            val state by viewModel.state.collectAsState()
            AccountScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = NavigationRoutes.Main.Add.route + "?food_id={food_id}",
            arguments = listOf(
                navArgument(
                    name = "food_id",
                ) {
                    type = NavType.IntType
                    defaultValue = -69
                }
            )
        ) {
            val viewModel = hiltViewModel<AddEditViewModel>()
            val state by viewModel.state.collectAsState()
            AddEditScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = NavigationRoutes.Main.ReportMamRates.route
        ) {
            val viewModel = hiltViewModel<ReportMamRatesViewModel>()
            val state by viewModel.state.collectAsState()
            ReportMamRatesScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composable(
            route = NavigationRoutes.Main.AccountSetting.route
        ) {
            val viewModel = hiltViewModel<AccountSettingViewModel>()
            val state by viewModel.state.collectAsState()
            AccountSettingScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = NavigationRoutes.Main.ChangePassword.route
        ) {
            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            val state by viewModel.state.collectAsState()
            ChangePasswordScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }

}
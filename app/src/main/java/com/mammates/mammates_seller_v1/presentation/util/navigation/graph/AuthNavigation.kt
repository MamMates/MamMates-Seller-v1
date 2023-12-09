package com.mammates.mammates_seller_v1.presentation.util.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mammates.mammates_seller_v1.presentation.pages.auth.forgot_password.ResetPasswordScreen
import com.mammates.mammates_seller_v1.presentation.pages.auth.forgot_password.ResetPasswordViewModel
import com.mammates.mammates_seller_v1.presentation.pages.auth.login.LoginScreen
import com.mammates.mammates_seller_v1.presentation.pages.auth.login.LoginViewModel
import com.mammates.mammates_seller_v1.presentation.pages.auth.register.RegisterScreen
import com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.RegisterFormScreen
import com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.RegisterFormViewModel
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = NavigationRoutes.Auth.route,
        startDestination = NavigationRoutes.Auth.Login.route
    ) {
        composable(route = NavigationRoutes.Auth.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.state.collectAsState()

            LoginScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = NavigationRoutes.Auth.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = NavigationRoutes.Auth.RegisterForm.route) {
            val viewModel = hiltViewModel<RegisterFormViewModel>()
            val state by viewModel.state.collectAsState()

            RegisterFormScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = NavigationRoutes.Auth.ResetPassword.route) {
            val viewModel = hiltViewModel<ResetPasswordViewModel>()
            val state by viewModel.state.collectAsState()

            ResetPasswordScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}
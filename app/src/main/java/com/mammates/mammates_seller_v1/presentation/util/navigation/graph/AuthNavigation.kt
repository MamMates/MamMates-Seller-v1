package com.mammates.mammates_seller_v1.presentation.util.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mammates.mammates_seller_v1.presentation.pages.login.LoginScreen
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

fun NavGraphBuilder.authGraph (navController: NavController){
    navigation(
        route = NavigationRoutes.Auth.route,
        startDestination = NavigationRoutes.Auth.Login.route
    ){
        composable(route = NavigationRoutes.Auth.Login.route){
            LoginScreen()
        }
        composable(route= NavigationRoutes.Auth.Register.route){

        }
    }
}
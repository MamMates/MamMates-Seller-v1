package com.mammates.mammates_seller_v1.presentation.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {

    if (!state.isOnBoarding && !state.isAuth) {
        LaunchedEffect(key1 = true) {
            navController.navigate(route = NavigationRoutes.Introduction.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    } else if (!state.isAuth) {
        LaunchedEffect(key1 = true) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = state.exampleState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        state = HomeState(),
        onEvent = {}
    )
}
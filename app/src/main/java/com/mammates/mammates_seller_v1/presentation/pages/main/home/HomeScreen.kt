package com.mammates.mammates_seller_v1.presentation.pages.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.pager.PagerIndicator
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.CardNavigation
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.cardNavigationItems
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )

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
                .fillMaxSize()
                .padding(horizontal = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(state = pagerState, pageSpacing = 20.dp) { page ->
                CardNavigation(
                    title = cardNavigationItems[page].title,
                    illustration = cardNavigationItems[page].illustration,
                    navController = navController,
                    route = cardNavigationItems[page].route
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            PagerIndicator(pagerState = pagerState)
            Spacer(modifier = Modifier.height(25.dp))
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
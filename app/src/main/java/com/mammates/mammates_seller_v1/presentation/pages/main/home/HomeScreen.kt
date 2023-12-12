package com.mammates.mammates_seller_v1.presentation.pages.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.domain.model.OrderRecentItems
import com.mammates.mammates_seller_v1.presentation.component.pager.PagerIndicator
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.CardArticle
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.CardNavigation
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.CardRecentOrder
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.cardArticleItems
import com.mammates.mammates_seller_v1.presentation.pages.main.home.component.cardNavigationItems
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.StatusOrder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )

    if (!state.isOnBoarding && !state.isAuth) {
        LaunchedEffect(key1 = Unit) {

            navController.navigate(route = NavigationRoutes.Introduction.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    } else if (!state.isAuth) {
        LaunchedEffect(key1 = Unit) {

            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Error Message")
            },
            text = {
                Text(
                    text = state.errorMessage,
                    textAlign = TextAlign.Center
                )

            },
            onDismissRequest = {
                state.errorMessage.isEmpty()
            },
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Alert Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(HomeEvent.OnDismissErrorDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }


    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
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

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Recently Order",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(15.dp))

            if (state.orderList.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No Order Recently",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center
                )
            } else {
                Column {
                    state.orderList.forEach { items ->
                        CardRecentOrder(
                            buyer = items.buyer ?: "No Buyer Name",
                            status = when (items.status) {
                                0 -> StatusOrder.Canceled
                                1 -> StatusOrder.Unconfirmed
                                2 -> StatusOrder.Confirmed
                                3 -> StatusOrder.Finish
                                else -> StatusOrder.Unidentified
                            },
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }


            Spacer(modifier = Modifier.height(25.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Article",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(15.dp))

            cardArticleItems.forEach { items ->
                CardArticle(
                    title = items.title,
                    illustration = items.illustration,
                    description = items.description
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        state = HomeState(
            orderList = listOf(
                OrderRecentItems(
                    buyer = "Mr. Tude",
                    status = 1
                ),
                OrderRecentItems(
                    buyer = "Mr. Tude",
                    status = 1
                ),
                OrderRecentItems(
                    buyer = "Mr. Tude",
                    status = 1
                )

            )
        ),
        onEvent = {}
    )
}
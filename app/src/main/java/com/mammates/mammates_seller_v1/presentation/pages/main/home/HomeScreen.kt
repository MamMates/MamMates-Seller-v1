package com.mammates.mammates_seller_v1.presentation.pages.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        // TODO : Implementing Recent Order

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
                        }
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
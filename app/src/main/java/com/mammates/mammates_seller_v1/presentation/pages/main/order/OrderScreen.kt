package com.mammates.mammates_seller_v1.presentation.pages.main.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.CardOrder
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.NoOrderLabel
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.tabTitleItem
import com.mammates.mammates_seller_v1.util.StatusOrder

@Composable
fun OrderScreen(
    navController: NavController,
    state: OrderState,
    onEvent: (OrderEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TabRow(selectedTabIndex = state.tabIndex) {
            tabTitleItem.forEachIndexed { index, tab ->
                Tab(selected = state.tabIndex == index,
                    onClick = {
                        onEvent(OrderEvent.OnChangeTab(index))
                    },
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        when (state.tabIndex) {
            0 -> if (state.unConfirmOrder.isNullOrEmpty()) {
                NoOrderLabel(statusOrder = StatusOrder.Unconfirmed.name)
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .verticalScroll(scrollState),
                ) {
                    state.unConfirmOrder.forEach { items ->
                        CardOrder(
                            statusOrder = StatusOrder.Unconfirmed,
                            buyer = items.buyer ?: "No Buyer",
                            total = items.total ?: 0,
                            foods = items.foods ?: emptyList()
                        )
                    }

                }
            }

            1 -> if (state.confirmOrder.isNullOrEmpty()) {
                NoOrderLabel(statusOrder = StatusOrder.Confirmed.name)
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .verticalScroll(scrollState),
                ) {
                    state.confirmOrder.forEach { items ->
                        CardOrder(
                            statusOrder = StatusOrder.Confirmed,
                            buyer = items.buyer ?: "No Buyer",
                            total = items.total ?: 0,
                            foods = items.foods ?: emptyList()
                        )
                    }

                }
            }

            2 -> if (state.finishOrder.isNullOrEmpty()) {
                NoOrderLabel(statusOrder = StatusOrder.Finish.name)
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .verticalScroll(scrollState),
                ) {
                    state.finishOrder.forEach { items ->
                        CardOrder(
                            statusOrder = StatusOrder.Finish,
                            buyer = items.buyer ?: "No Buyer",
                            total = items.total ?: 0,
                            foods = items.foods ?: emptyList()
                        )
                    }

                }
            }

            3 -> if (state.canceledOrder.isNullOrEmpty()) {
                NoOrderLabel(statusOrder = StatusOrder.Canceled.name)
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .verticalScroll(scrollState),
                ) {
                    state.canceledOrder.forEach { items ->
                        CardOrder(
                            statusOrder = StatusOrder.Canceled,
                            buyer = items.buyer ?: "No Buyer",
                            total = items.total ?: 0,
                            foods = items.foods ?: emptyList()
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(
        navController = rememberNavController(),
        state = OrderState(
            tabIndex = 0,
        ),
        onEvent = {}
    )
}
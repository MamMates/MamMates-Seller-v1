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

        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .verticalScroll(scrollState),
        ) {

            CardOrder(
                buyer = "Gede Mahardika",
                statusOrder = StatusOrder.Unconfirmed,
                total = 40000
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardOrder(
                buyer = "Gede Mahardika",
                statusOrder = StatusOrder.Confirmed,
                total = 40000
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardOrder(
                buyer = "Gede Mahardika",
                statusOrder = StatusOrder.Unconfirmed,
                total = 40000
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardOrder(
                buyer = "Gede Mahardika",
                statusOrder = StatusOrder.Unconfirmed,
                total = 40000
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(
        navController = rememberNavController(),
        state = OrderState(),
        onEvent = {}
    )
}
package com.mammates.mammates_seller_v1.presentation.pages.main.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.CardOrder
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.NoOrderLabel
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.tabTitleItem
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.StatusOrder

@Composable
fun OrderScreen(
    navController: NavController,
    state: OrderState,
    onEvent: (OrderEvent) -> Unit
) {

    LaunchedEffect(key1 = state.token) {
        if (state.token.isEmpty()) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.isConfirmDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Confirm the action")
            },
            text = {
                Text(text = "Are you sure wanna change this order status ?")
            },
            onDismissRequest = {
                !state.isConfirmDialogOpen
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderEvent.OnConfirmChangeStatus)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderEvent.OnDismissChangeStatusDialog)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
    if (state.isConfirmCanceledDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Confirm the action")
            },
            text = {
                Text(text = "Are you sure wanna cancel this order ?")
            },
            onDismissRequest = {
                !state.isConfirmCanceledDialogOpen
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderEvent.OnConfirmChangeStatus)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderEvent.OnDismissChangeStatusDialog)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Error !")
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
                Icon(Icons.Default.Info, contentDescription = "Error Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(OrderEvent.OnDismissErrorDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }
    if (!state.successMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Success !")
            },
            text = {

                Text(
                    text = state.successMessage,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = {
                state.successMessage.isEmpty()
            },
            icon = {
                Icon(Icons.Default.CheckCircle, contentDescription = "Success Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(OrderEvent.OnDismissErrorDialog)
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

            if (state.orders.isNullOrEmpty()) {
                NoOrderLabel(
                    statusOrder = when (state.tabIndex) {
                        0 -> StatusOrder.Unconfirmed.name
                        1 -> StatusOrder.Confirmed.name
                        2 -> StatusOrder.Finish.name
                        3 -> StatusOrder.Canceled.name
                        else -> StatusOrder.Unidentified.name
                    }
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                ) {
                    items(state.orders) { item ->
                        CardOrder(
                            statusOrder = when (state.tabIndex) {
                                0 -> StatusOrder.Unconfirmed
                                1 -> StatusOrder.Confirmed
                                2 -> StatusOrder.Finish
                                3 -> StatusOrder.Canceled
                                else -> StatusOrder.Unidentified
                            },
                            buyer = item.buyer ?: "No Buyer",
                            total = item.total ?: 0,
                            foods = item.foods ?: emptyList(),
                            onConfirmOrder = {
                                onEvent(
                                    OrderEvent.OnOpenChangeStatusDialog(
                                        id = item.id ?: -69,
                                        statusTarget = when (item.status) {
                                            1 -> StatusOrder.Confirmed
                                            2 -> StatusOrder.Finish
                                            else -> StatusOrder.Unidentified
                                        },
                                    )
                                )
                            },
                            onSeeDetail = {
                                navController.navigate(NavigationRoutes.Main.OrderDetail.route + "?order_id=${item.id}")
                            },
                            onCancelOrder = {
                                onEvent(
                                    OrderEvent.OnOpenChangeStatusDialog(
                                        id = item.id ?: -69,
                                        statusTarget = StatusOrder.Canceled
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
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
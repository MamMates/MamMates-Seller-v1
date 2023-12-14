package com.mammates.mammates_seller_v1.presentation.pages.main.order_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.mammates.mammates_seller_v1.presentation.component.text.TextLabelValue
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.CardOrderFood
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.StatusOrder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailScreen(
    navController: NavController,
    state: OrderDetailState,
    onEvent: (OrderDetailEvent) -> Unit
) {

    val scrollState = rememberScrollState()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLoading, onRefresh = {
        onEvent(OrderDetailEvent.OnRefreshPage)
    })

    LaunchedEffect(key1 = state.token) {
        if (state.token.isEmpty()) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.isNotAuthorizeDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Please Login")
            },
            text = {
                Text(
                    text = "You must login to continue !",
                    textAlign = TextAlign.Center
                )

            },
            onDismissRequest = {
                state.isNotAuthorizeDialogOpen
            },
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Alert Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(OrderDetailEvent.OnDismissNotAuthorize)
                }) {
                    Text(text = "Login")

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
                    onEvent(OrderDetailEvent.OnDismissDialog)
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
                    onEvent(OrderDetailEvent.OnDismissDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
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
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderDetailEvent.OnChangeStatusOrder())
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderDetailEvent.OnDismissDialog)
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
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderDetailEvent.OnChangeStatusOrder(isCanceled = true))
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(OrderDetailEvent.OnDismissDialog)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }


    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 35.dp, end = 35.dp, bottom = 20.dp),
            ) {
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Order Detail",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    TextLabelValue(label = "Invoice", value = state.invoice)
                    TextLabelValue(label = "Name", value = state.buyer)
                    TextLabelValue(label = "Tanggal Pembelian", value = state.date)
                    Spacer(modifier = Modifier.height(20.dp))
                    if (state.foods.isNullOrEmpty()) {
                        Box(modifier = Modifier.padding(vertical = 50.dp)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "No Foods",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    } else {
                        state.foods.forEach { item ->
                            CardOrderFood(
                                foodName = item.name ?: "No Name",
                                quantity = item.quantity ?: 0,
                                image = item.image,
                                price = item.price ?: 0
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                    TextLabelValue(
                        label = "Status",
                        statusOrder = state.status
                    )
                    TextLabelValue(
                        label = "Total",
                        value = "Rp. ${state.total}"
                    )
                    TextLabelValue(
                        label = "Payment Method",
                        value = "Cash On Delivery"
                    )


                }
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .align(Alignment.BottomCenter)
                ) {
                    if (
                        state.status != StatusOrder.Canceled &&
                        state.status != StatusOrder.Finish &&
                        state.status != StatusOrder.Unidentified
                    ) {
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onEvent(OrderDetailEvent.OnOpenConfirmDialog())
                            }
                        ) {
                            Text(
                                text = when (state.status) {
                                    StatusOrder.Unconfirmed -> "Confirm Order"
                                    StatusOrder.Confirmed -> "Finish Order"
                                    else -> "Confirm Order"
                                }
                            )
                        }
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(OrderDetailEvent.OnOpenConfirmDialog(isCanceled = true))
                        }
                    ) {
                        Text(text = "Cancel Order")
                    }
                }
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isLoading,
            state = pullRefreshState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailScreenPreview() {
    OrderDetailScreen(
        navController = rememberNavController(),
        state = OrderDetailState(
            buyer = "Tude Maha",
            invoice = "INV/EHE/EHE",
            date = "12 Maret 2023",
            total = 40000
        ),
        onEvent = {}
    )
}
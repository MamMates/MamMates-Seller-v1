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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.currentStateAsState
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.dialog.ConfirmDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.ErrorDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.SuccessDialog
import com.mammates.mammates_seller_v1.presentation.component.loading.LoadingScreen
import com.mammates.mammates_seller_v1.presentation.component.text.TextLabelValue
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.CardOrderFood
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.StatusOrder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailScreen(
    navController: NavController,
    state: OrderDetailState,
    onEvent: (OrderDetailEvent) -> Unit
) {

    val scrollState = rememberScrollState()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefresh, onRefresh = {
        onEvent(OrderDetailEvent.OnRefreshPage)
    })
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                onEvent(OrderDetailEvent.OnRefreshPage)
            }

            else -> {}
        }
    }

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
        ErrorDialog(
            message = HttpError.UNAUTHORIZED.message,
            onConfirm = {
                onEvent(OrderDetailEvent.ClearToken)
            },
            title = "Unauthorized User !"
        )
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        ErrorDialog(message = state.errorMessage, onConfirm = {
            onEvent(OrderDetailEvent.OnDismissDialog)
        })
    }
    if (!state.successMessage.isNullOrEmpty()) {
        SuccessDialog(
            message = state.successMessage,
            onConfirm = {
                onEvent(OrderDetailEvent.OnDismissDialog)
                onEvent(OrderDetailEvent.OnRefreshPage)
            }
        )
    }

    if (state.isConfirmDialogOpen) {
        ConfirmDialog(
            message = "Are you sure wanna change this order status ?",
            onConfirm = {
                onEvent(OrderDetailEvent.OnChangeStatusOrder())
            },
            onDismiss = {
                onEvent(OrderDetailEvent.OnDismissDialog)
            }
        )
    }
    if (state.isConfirmCanceledDialogOpen) {
        ConfirmDialog(
            message = "Are you sure wanna cancel this order ?",
            onConfirm = {
                onEvent(OrderDetailEvent.OnChangeStatusOrder(isCanceled = true))
            },
            onDismiss = {
                onEvent(OrderDetailEvent.OnDismissDialog)
            }
        )
    }


    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
        if (state.isLoading) {
            LoadingScreen()
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
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isRefresh,
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
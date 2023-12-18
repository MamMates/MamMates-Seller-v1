package com.mammates.mammates_seller_v1.presentation.pages.main.order

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
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
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.CardOrder
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.NoOrderLabel
import com.mammates.mammates_seller_v1.presentation.pages.main.order.component.tabTitleItem
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.StatusOrder

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun OrderScreen(
    navController: NavController, state: OrderState, onEvent: (OrderEvent) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefresh, onRefresh = {
        onEvent(OrderEvent.OnRefreshPage)
    })
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateAsState()


    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                onEvent(OrderEvent.OnRefreshPage)
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
                onEvent(OrderEvent.ClearToken)
            },
            title = "Unauthorized User !"
        )
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        ErrorDialog(
            message = state.errorMessage,
            onConfirm = {
                onEvent(OrderEvent.OnDismissErrorDialog)
            }
        )
    }

    if (state.isConfirmDialogOpen) {
        ConfirmDialog(
            message = "Are you sure wanna change this order status ?",
            onConfirm = {
                onEvent(OrderEvent.OnConfirmChangeStatus)
            },
            onDismiss = {
                onEvent(OrderEvent.OnDismissChangeStatusDialog)
            }
        )
    }
    if (state.isConfirmCanceledDialogOpen) {
        ConfirmDialog(
            message = "Are you sure wanna cancel this order ?",
            onConfirm = {
                onEvent(OrderEvent.OnConfirmChangeStatus)
            },
            onDismiss = {
                onEvent(OrderEvent.OnDismissChangeStatusDialog)
            }
        )
    }

    if (!state.successMessage.isNullOrEmpty()) {
        SuccessDialog(
            message = state.successMessage,
            onConfirm = {
                onEvent(OrderEvent.OnDismissErrorDialog)
            }
        )
    }
    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
        if (state.isLoading) {
            LoadingScreen()
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TabRow(selectedTabIndex = state.tabIndex) {
                    tabTitleItem.forEachIndexed { index, tab ->
                        Tab(selected = state.tabIndex == index, onClick = {
                            onEvent(OrderEvent.OnChangeTab(index))
                        }, text = {
                            Text(
                                text = tab,
                                style = MaterialTheme.typography.titleSmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        })
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                if (state.orders.isNullOrEmpty()) {

                    NoOrderLabel(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
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
                        modifier = Modifier.padding(horizontal = 35.dp)
                    ) {
                        items(state.orders, key = {
                            try {
                                it.id ?: 0
                            } catch (e: IllegalArgumentException) {
                                -69
                            }
                        }) { item ->
                            CardOrder(
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    )
                                ),
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
                                            id = item.id ?: -69, statusTarget = StatusOrder.Canceled
                                        )
                                    )
                                })
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
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
fun OrderScreenPreview() {
    OrderScreen(navController = rememberNavController(), state = OrderState(
        tabIndex = 0,
    ), onEvent = {})
}
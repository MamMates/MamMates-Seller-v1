package com.mammates.mammates_seller_v1.presentation.pages.main.store

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.mammates.mammates_seller_v1.presentation.pages.main.store.component.CardFood
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.Rating

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun StoreScreen(
    navController: NavController,
    state: StoreState,
    onEvent: (StoreEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {
            }

            Lifecycle.State.STARTED -> {
                onEvent(StoreEvent.OnRefreshPage)
            }

            Lifecycle.State.RESUMED -> {

            }
        }
    }

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLoading, onRefresh = {
        onEvent(StoreEvent.OnRefreshPage)
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
                    onEvent(StoreEvent.OnDismissNotAuthorize)
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
                    onEvent(StoreEvent.OnDismissDialog)
                }) {
                    Text(text = "Okay")

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
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigate(NavigationRoutes.Main.Add.route + "?food_id=-69")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Adding food"
                        )
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 35.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.storeName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location Icons",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = state.storeAddress,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    if (state.foods.isNullOrEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "No Food",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        LazyColumn {
                            items(state.foods, key = {
                                try {
                                    it.id ?: 0
                                } catch (e: IllegalArgumentException) {
                                    -69
                                }
                            }) { item ->
                                CardFood(
                                    modifier = Modifier.animateItemPlacement(
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing,

                                            ),
                                    ),
                                    rating = when (item.mamRates) {
                                        1 -> Rating.ONE
                                        2 -> Rating.TWO
                                        3 -> Rating.THREE
                                        else -> Rating.ZERO
                                    },
                                    foodName = item.name ?: "No Name",
                                    price = item.price ?: 0,
                                    image = item.image,
                                    isValid = item.isValid ?: false,
                                    onClickCard = {
                                        navController.navigate(NavigationRoutes.Main.Add.route + "?food_id=${item.id}")
                                    }
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
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

@Preview
@Composable
fun StoreScreenPreview() {
    StoreScreen(
        navController = rememberNavController(),
        state = StoreState(
        ),
        onEvent = {}
    )
}
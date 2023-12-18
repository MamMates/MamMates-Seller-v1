package com.mammates.mammates_seller_v1.presentation.pages.main.account

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.currentStateAsState
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.mammates.mammates_seller_v1.common.Constants
import com.mammates.mammates_seller_v1.presentation.component.dialog.ConfirmDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.ErrorDialog
import com.mammates.mammates_seller_v1.presentation.component.loading.LoadingScreen
import com.mammates.mammates_seller_v1.presentation.pages.main.account.component.CardAccount
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.HttpError

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    navController: NavController, state: AccountState, onEvent: (AccountEvent) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefresh, onRefresh = {
        onEvent(AccountEvent.OnRefreshPage)
    })
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                onEvent(AccountEvent.OnRefreshPage)
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

    if (!state.errorMessage.isNullOrEmpty()) {
        ErrorDialog(
            message = state.errorMessage,
            onConfirm = {
                onEvent(AccountEvent.OnDismissDialog)
            }
        )
    }
    if (state.isNotAuthorizeDialogOpen) {
        ErrorDialog(
            message = HttpError.UNAUTHORIZED.message,
            onConfirm = {
                onEvent(AccountEvent.ClearToken)
            }
        )
    }

    if (state.isConfirmLogoutOpen) {
        ConfirmDialog(
            title = "Logout",
            message = "Are you sure wanna logout ?",
            onConfirm = {
                onEvent(AccountEvent.OnDismissDialog)
                onEvent(AccountEvent.ClearToken)
            },
            onDismiss = {
                onEvent(AccountEvent.OnDismissDialog)
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 35.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        model = if (state.storeImage.isNullOrEmpty()) {
                            Constants.DUMMY_PHOTO_PROFILE
                        } else {
                            state.storeImage
                        },
                        contentDescription = "Store Profile",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = state.storeName, style = MaterialTheme.typography.headlineSmall
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                CardAccount(
                    onClick = {
                        navController.navigate(NavigationRoutes.Main.AccountSetting.route)
                    },
                    title = "Account Setting",
                )
                Spacer(modifier = Modifier.height(5.dp))
                CardAccount(
                    title = "Change Password",
                    onClick = {
                        navController.navigate(NavigationRoutes.Main.ChangePassword.route)
                    },
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    onEvent(AccountEvent.OnOpenConfirmDialogLogout)
                }) {
                    Text(text = "Logout")
                }
                Spacer(modifier = Modifier.height(20.dp))
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
fun AccountScreenPreview() {
    AccountScreen(navController = rememberNavController(), state = AccountState(
        storeName = "Pecel Lele Bro Waw Murah Meriah"
    ), onEvent = {})
}
package com.mammates.mammates_seller_v1.presentation.pages.main.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.presentation.pages.main.account.component.CardAccount
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun AccountScreen(
    navController: NavController,
    state: AccountState,
    onEvent: (AccountEvent) -> Unit
) {

    LaunchedEffect(key1 = state.isAuth) {
        if (!state.isAuth) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                painter = painterResource(id = R.drawable.dummy_food),
                contentDescription = "Store Profile",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = state.storeName,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        CardAccount(
            navController = navController,
            title = "Account Setting",
            route = NavigationRoutes.Main.AccountSetting.route
        )
        Spacer(modifier = Modifier.height(5.dp))
        CardAccount(
            navController = navController,
            title = "Change Password",
            route = NavigationRoutes.Main.ChangePassword.route
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(AccountEvent.OnLogout)
            }
        ) {
            Text(text = "Logout")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        navController = rememberNavController(),
        state = AccountState(
            storeName = "Pecel Lele Bro Waw Murah Meriah"
        ),
        onEvent = {}
    )
}
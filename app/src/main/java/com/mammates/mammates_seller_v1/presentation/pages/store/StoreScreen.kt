package com.mammates.mammates_seller_v1.presentation.pages.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun StoreScreen(
    navController: NavController,
    state: StoreState,
    onEvent: (StoreEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = state.exampleState)
    }
}

@Preview
@Composable
fun StoreScreenPreview() {
    StoreScreen(
        navController = rememberNavController(),
        state = StoreState(),
        onEvent = {}
    )
}
package com.mammates.mammates_seller_v1.presentation.pages.mam_rates

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
fun MamRatesScreen(
    navController: NavController,
    state: MamRatesState,
    onEvent: (MamRatesEvent) -> Unit
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

@Preview(showBackground = true)
@Composable
fun MamRatesScreenPreview() {
    MamRatesScreen(
        navController = rememberNavController(),
        state = MamRatesState(),
        onEvent = {}
    )
}
package com.mammates.mammates_seller_v1.presentation.pages.main.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CameraScreen(
    navController: NavController,
    state : CameraState,
    onEvent : (CameraEvent) -> Unit
) {

}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    CameraScreen(
        navController = rememberNavController(),
        state = CameraState(),
        onEvent = {}
    )
}
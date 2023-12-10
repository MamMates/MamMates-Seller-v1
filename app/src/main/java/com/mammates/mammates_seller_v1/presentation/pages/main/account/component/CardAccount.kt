package com.mammates.mammates_seller_v1.presentation.pages.main.account.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CardAccount(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    route: String
) {
    Column(
        modifier = modifier
            .padding(vertical = 10.dp)
            .clickable {
                navController.navigate(route)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Icon Arrow"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun CardAccountPreview() {
    CardAccount(
        title = "Account Setting",
        route = "",
        navController = rememberNavController()
    )
}
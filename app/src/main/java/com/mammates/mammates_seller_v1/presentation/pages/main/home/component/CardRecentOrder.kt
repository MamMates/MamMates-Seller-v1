package com.mammates.mammates_seller_v1.presentation.pages.main.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.StatusOrder

@Composable
fun CardRecentOrder(
    modifier: Modifier = Modifier,
    buyer: String,
    status: StatusOrder,
    navController: NavController
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20)

            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(20)
            )
            .clickable {
                navController.navigate(NavigationRoutes.Main.Order.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = buyer,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = status.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Arrow Icon"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardRecentOrderPreview() {
    CardRecentOrder(
        buyer = "Mr. Tude",
        status = StatusOrder.Unconfirmed,
        navController = rememberNavController()
    )
}
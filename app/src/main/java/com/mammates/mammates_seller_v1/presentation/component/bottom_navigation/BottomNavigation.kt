package com.mammates.mammates_seller_v1.presentation.component.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, items ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(items.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = items.icon,
                        contentDescription = items.label
                    )
                },
                label = {
                    Text(
                        text = items.label
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        navController = rememberNavController(),
        items = bottomNavigationItem
    )
}

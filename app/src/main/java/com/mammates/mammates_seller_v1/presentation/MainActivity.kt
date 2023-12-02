package com.mammates.mammates_seller_v1.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.bottom_navigation.BottomNavigation
import com.mammates.mammates_seller_v1.presentation.component.bottom_navigation.bottomNavigationItem
import com.mammates.mammates_seller_v1.presentation.component.top_navigation.TopBackNavigation
import com.mammates.mammates_seller_v1.presentation.component.top_navigation.TopNavigation
import com.mammates.mammates_seller_v1.presentation.theme.MamMatesSellerv1Theme
import com.mammates.mammates_seller_v1.presentation.util.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            MamMatesSellerv1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (bottomNavigationItem.firstOrNull { it.route == currentRoute } != null) {
                                BottomNavigation(
                                    navController = navController,
                                    items = bottomNavigationItem
                                )
                            }
                        },

                        topBar = {
                            if (bottomNavigationItem.firstOrNull { it.route == currentRoute } != null) {
                                TopNavigation()
                            }
                        }
                    ) { innerPadding ->
                        NavigationGraph(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

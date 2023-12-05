package com.mammates.mammates_seller_v1.presentation.pages.on_boarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component.OnBoardingPager
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component.OnBoardingPagerIndicator
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component.onBoardingPagerItem
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController
) {

    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            OnBoardingPager(
                title = onBoardingPagerItem[page].title,
                description = onBoardingPagerItem[page].description,
                illustration = painterResource(id = onBoardingPagerItem[page].drawableRes)
            )

        }
        Spacer(modifier = Modifier.height(40.dp))

        OnBoardingPagerIndicator(pagerState = pagerState)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage != pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        navController.popBackStack()
                        navController.navigate(NavigationRoutes.Auth.route)
                    }
                }
            }
        ) {
            Text(text = "Get Started")
        }


    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        navController = rememberNavController()
    )
}
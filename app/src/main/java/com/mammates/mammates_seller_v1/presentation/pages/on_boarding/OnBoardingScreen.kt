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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.mammates.mammates_seller_v1.presentation.component.pager.PagerIndicator
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component.OnBoardingPager
import com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component.onBoardingPagerItem
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    state: OnBoardingState,
    onEvent: (OnBoardingEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 35.dp, end = 35.dp, top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        PagerIndicator(pagerState = pagerState)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage != pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onEvent(OnBoardingEvent.SetIntroIsDone)
                        navController.navigate(NavigationRoutes.Auth.route) {
                            popUpTo(NavigationRoutes.Introduction.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        ) {
            Text(text = "Get Started")
        }
        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        navController = rememberNavController(),
        onEvent = {},
        state = OnBoardingState()
    )
}
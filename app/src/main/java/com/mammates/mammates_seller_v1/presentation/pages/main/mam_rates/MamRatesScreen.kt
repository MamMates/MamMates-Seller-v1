package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.Rating

@Composable
fun MamRatesScreen(
    navController: NavController,
    state: MamRatesState,
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = state.token) {
        if (state.token.isEmpty()) {
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
            .padding(35.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.mam_rates_illustration),
            contentDescription = "MamRates Illustration"
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "What is MamRates",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Introducing MamRates – our revolutionary feature that uses advanced AI to provide instant credibility ratings for your food based on visual appeal and quality. Elevate your offerings and build trust with customers effortlessly on MamMates.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.height(15.dp))
        RatingDisplay(
            rating = Rating.TWO
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Experience MamRates, where a 1-3 star scale, represented by stars, vividly showcases the allure and viability of your culinary creations. The higher the rating, the more enticing and credible your food product becomes, ensuring a larger appeal to discerning customers",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(NavigationRoutes.Main.Result.route)
            }
        ) {
            Text(text = "See Your Food MamRates")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MamRatesScreenPreview() {
    MamRatesScreen(
        navController = rememberNavController(),
        state = MamRatesState(),
    )
}
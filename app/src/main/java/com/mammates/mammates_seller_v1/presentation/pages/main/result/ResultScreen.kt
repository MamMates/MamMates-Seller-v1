package com.mammates.mammates_seller_v1.presentation.pages.main.result

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay

@Composable
fun ResultScreen(
    navController: NavController,
    state: ResultState,
    onEvent: (ResultEvent) -> Unit
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Your MamRates",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(15.dp))
        RatingDisplay(
            rating = state.rating,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Your MamRates are in! Your culinary creation has earned ${state.rating.rating} stars, showcasing its visual appeal and quality. Keep delighting taste buds and watch your ratings soar on MamMates!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Category:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Text(
                text = state.category,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )

        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Price Sugestion:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Text(
                text = "Rp. ${state.price}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )

        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "How to increase the MamRates ?",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Elevating your MamRates is simple – consider infusing your dish with creative presentations or exciting flavors that captivate the senses. By enhancing the visual appeal and taste of your culinary creation, you can ensure a higher MamRates score and make a lasting impression on discerning customers. Embrace innovation and watch as your food becomes a standout star on MamMates",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Take Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        navController = rememberNavController(),
        state = ResultState(),
        onEvent = {}
    )
}
package com.mammates.mammates_seller_v1.presentation.pages.on_boarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.R

@Composable
fun OnBoardingPager(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    illustration: Painter
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = illustration,
            contentDescription = "Pager Illustration",
        )
        Spacer(
            modifier = Modifier.height(60.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagerPreview() {
    OnBoardingPager(
        title = "Sell with Impact: Mitra MamMates",
        description = "Become a seller with purpose on Mitra MamMates, where your contributions go beyond transactions, actively aiding the environment by reducing food waste.",
        illustration = painterResource(id = R.drawable.pager_mitra)
    )
}
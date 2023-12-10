package com.mammates.mammates_seller_v1.presentation.pages.main.order.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mammates.mammates_seller_v1.R

@Composable
fun CardOrderFood(
    modifier: Modifier = Modifier,
    foodName: String,
    quantity: Int,
    image : String?,
    price: Int // TODO : Currency type
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.width(60.dp)
                .height(60.dp),
            model = ImageRequest.Builder(context)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Food Picture",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.dummy_food)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = foodName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Quantity: $quantity",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Rp. $price",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardOrderFoodPreview() {
    CardOrderFood(
        foodName = "Donut Kentang rasa coklat",
        quantity = 4,
        price = 20000,
        image = ""
    )
}
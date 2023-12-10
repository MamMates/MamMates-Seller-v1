package com.mammates.mammates_seller_v1.presentation.pages.main.store.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.util.Rating

@Composable
fun CardFood(
    modifier: Modifier = Modifier,
    rating: Rating,
    foodName: String,
    price: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Image(
            modifier = Modifier.width(60.dp),
            painter = painterResource(id = R.drawable.dummy_food),
            contentDescription = "Food Thumbnail"
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = foodName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            RatingDisplay(rating = rating)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Rp. $price",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CardFoodPreview() {
    CardFood(
        rating = Rating.TWO,
        foodName = "Donut Keju Suka Terbang",
        price = 5000,
    )
}
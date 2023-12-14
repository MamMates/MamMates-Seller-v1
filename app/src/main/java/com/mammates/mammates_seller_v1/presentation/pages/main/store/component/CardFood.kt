package com.mammates.mammates_seller_v1.presentation.pages.main.store.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.common.Constants
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.util.Rating

@Composable
fun CardFood(
    modifier: Modifier = Modifier,
    rating: Rating,
    foodName: String,
    price: Int,
    image: String?,
    isValid: Boolean,
    onClickCard: () -> Unit
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)
            )
            .clickable {
                onClickCard()
            }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp),
            model = if (!image.isNullOrEmpty()) {
                image
            } else {
                Constants.DUMMY_PHOTO
            },
            contentDescription = "Food Thumbnail",
            placeholder = painterResource(id = R.drawable.dummy_food),
            colorFilter = if (!isValid) {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            } else {
                null
            },
            contentScale = ContentScale.Crop

        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            if (!isValid) {
                Text(
                    text = "Not Updated !",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
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
        image = "",
        isValid = true,
        onClickCard = {}
    )
}
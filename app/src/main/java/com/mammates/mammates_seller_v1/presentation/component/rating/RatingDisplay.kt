package com.mammates.mammates_seller_v1.presentation.component.rating

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mammates.mammates_seller_v1.util.Rating

@Composable
fun RatingDisplay(
    modifier: Modifier = Modifier,
    rating: Rating
) {
    Row {
        Icon(
            modifier = modifier,
            imageVector = if (
                rating == Rating.ONE ||
                rating == Rating.TWO ||
                rating == Rating.THREE
            ) {
                Icons.Default.Star
            } else {
                Icons.Outlined.StarOutline
            },
            contentDescription = "Star One",
            tint = MaterialTheme.colorScheme.secondary,

            )
        Icon(
            modifier = modifier,
            imageVector = if (
                rating == Rating.TWO ||
                rating == Rating.THREE
            ) {
                Icons.Default.Star
            } else {
                Icons.Outlined.StarOutline
            },
            contentDescription = "Star Two",
            tint = MaterialTheme.colorScheme.secondary

        )
        Icon(
            modifier = modifier,
            imageVector = if (
                rating == Rating.THREE
            ) {
                Icons.Default.Star
            } else {
                Icons.Outlined.StarOutline
            },
            contentDescription = "Star Three",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingDisplayPreview() {
    RatingDisplay(
        rating = Rating.THREE
    )
}
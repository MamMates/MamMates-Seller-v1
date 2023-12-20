package com.mammates.mammates_seller_v1.presentation.component.rating

import androidx.compose.foundation.layout.Box
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
    if (rating != Rating.Undefine) {

        Row {
            Icon(
                modifier = modifier,
                imageVector = if (
                    rating == Rating.ZERO ||
                    rating == Rating.ONE ||
                    rating == Rating.TWO
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
                    rating == Rating.ONE ||
                    rating == Rating.TWO
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
                    rating == Rating.TWO
                ) {
                    Icons.Default.Star
                } else {
                    Icons.Outlined.StarOutline
                },
                contentDescription = "Star Three",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    } else {
        Box {

        }
    }
}

/**
 * UP Rate Rating
 *
 * 0 -> one star
 * 1 -> two star
 * 2 -> three star
 *
 */

@Preview(showBackground = true)
@Composable
fun RatingDisplayPreview() {
    RatingDisplay(
        rating = Rating.TWO
    )
}
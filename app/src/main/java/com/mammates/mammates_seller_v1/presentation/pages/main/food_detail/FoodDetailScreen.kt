package com.mammates.mammates_seller_v1.presentation.pages.main.food_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField

@Composable
fun FoodDetailScreen(
    navController: NavController,
    state: FoodDetailState,
    onEvent: (FoodDetailEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Your Food",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormImageTextField(
            label = "Food Display",
            description = "Enhance your store's appeal with our food display feature "
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.foodName,
            onValueChange = {},
            errorResult = state.foodNameValidation,
            label = "Food Name",
            description = "Name your culinary creations and make them stand out on MamMates",
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = "${state.foodPrice ?: "Not Set"}",
            onValueChange = {},
            errorResult = state.foodPriceValidation,
            label = "Food Price (Rp)",
            description = "Input your desired prices and attract food enthusiasts on MamMates.",
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormImageTextField(
            label = "MamRates",
            description = "Upload a photo for automatic MamRates. Once generated, ratings cannot be changed",
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RatingDisplay(
                rating = state.rating
            )
            FilledTonalButton(onClick = { /*TODO*/ }) {
                Text(text = "Generate")

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.foodCategory ?: "Please generate your Rating !",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.suggestion,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Justify,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Data doesn’t match ?",
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(
                contentPadding = PaddingValues(0.dp),
                onClick = {}
            ) {
                Text(
                    text = "Reports",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Update Food")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodDetailScreen(
        navController = rememberNavController(),
        state = FoodDetailState(),
        onEvent = {}
    )
}
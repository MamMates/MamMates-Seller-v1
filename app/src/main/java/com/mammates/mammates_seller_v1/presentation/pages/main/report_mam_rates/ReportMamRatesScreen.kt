package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField

@Composable
fun ReportMamRatesScreen(
    navController: NavController,
    state: ReportMamRatesState,
    onEvent: (ReportMamRatesEvent) -> Unit
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
            text = "Report MamRates",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormImageTextField(
            label = "Food Display",
            description = "blablala",
            onImageCapture = {

            },
            imageUri = state.foodImage
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.foodName,
            onValueChange = {},
            errorResult = state.foodNameValidation,
            label = "Food Name",
            description = "blablabla",
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = "${state.foodPrice ?: "Not Set"}",
            onValueChange = {},
            errorResult = state.foodPriceValidation,
            label = "Food Price (Rp)",
            description = "blablala",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = "${state.rating}",
            onValueChange = {},
            errorResult = state.ratingValidation,
            label = "Your Preferable Rating (0-3)",
            description = "blablala",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Report")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportMamRatesScreenPreview(

) {
    ReportMamRatesScreen(
        navController = rememberNavController(),
        state = ReportMamRatesState(),
        onEvent = {}
    )
}
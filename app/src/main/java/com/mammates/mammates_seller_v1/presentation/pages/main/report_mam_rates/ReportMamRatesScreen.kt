package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.dialog.ErrorDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.SuccesDialog
import com.mammates.mammates_seller_v1.presentation.component.loading.LoadingScreen
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField
import com.mammates.mammates_seller_v1.util.HttpError

@Composable
fun ReportMamRatesScreen(
    navController: NavController,
    state: ReportMamRatesState,
    onEvent: (ReportMamRatesEvent) -> Unit
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    if (state.isNotAuthorizeDialogOpen) {
        ErrorDialog(
            message = HttpError.UNAUTHORIZED.message,
            onConfirm = {
                onEvent(ReportMamRatesEvent.ClearToken)
            },
        )
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        ErrorDialog(
            message = state.errorMessage,
            onConfirm = {
                onEvent(ReportMamRatesEvent.OnDismissDialog)
            }
        )
    }
    if (!state.successMessage.isNullOrEmpty()) {
        SuccesDialog(
            message = state.successMessage,
            onConfirm = {
                onEvent(ReportMamRatesEvent.OnDismissDialog)
                navController.popBackStack()
            }
        )
    }

    if (state.isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
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
                description = "Please submit a photo of your food in the report section for validation purposes. Your cooperation is vital in ensuring the accuracy and quality of our platform.",
                onImageCapture = {
                    onEvent(ReportMamRatesEvent.OnChangeFoodImageUri(it))
                },
                imageUri = state.foodImage,
                validationText = state.foodImageValidation

            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.foodName,
                onValueChange = {
                    onEvent(ReportMamRatesEvent.OnChangeFoodName(it))
                },
                errorResult = state.foodNameValidation,
                label = "Food Name",
                description = "Kindly provide the accurate name of your food in the designated report section.",
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = if (state.foodPrice == 0) {
                    ""
                } else {
                    "${state.foodPrice}"
                },
                onValueChange = {
                    onEvent(ReportMamRatesEvent.OnChangeFoodPrice(it))
                },
                errorResult = state.foodPriceValidation,
                label = "Food Price (Rp)",
                description = "Please enter the accurate price for your food in the report section.",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = if (state.rating == 0) {
                    ""
                } else {
                    "${state.rating}"
                },
                onValueChange = {
                    onEvent(ReportMamRatesEvent.OnChangeRating(it))
                },
                errorResult = state.ratingValidation,
                label = "Your Preferable Rating (0-3)",
                description = "Kindly input the correct rating for your food in the report section.",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(ReportMamRatesEvent.OnSubmitReport(context))
                }
            ) {
                Text(text = "Report")
            }
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
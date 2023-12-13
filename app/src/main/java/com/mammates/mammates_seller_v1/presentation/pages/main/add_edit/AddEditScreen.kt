package com.mammates.mammates_seller_v1.presentation.pages.main.add_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun AddEditScreen(
    navController: NavController,
    state: AddEditState,
    onEvent: (AddEditEvent) -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    if (!state.errorMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Error !")
            },
            text = {

                Text(
                    text = state.errorMessage,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = {
                state.errorMessage.isEmpty()
            },
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Error Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(AddEditEvent.OnDismissDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }
    if (!state.successMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Success !")
            },
            text = {
                Text(
                    text = state.successMessage,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = {
                state.successMessage.isEmpty()
            },
            icon = {
                Icon(Icons.Default.CheckCircle, contentDescription = "Success Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(AddEditEvent.OnDismissDialog)
                    navController.navigate(NavigationRoutes.Main.Store.route) {
                        popUpTo(NavigationRoutes.Main.Add.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }

    if (state.isConfirmDeleteDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Confirm the action")
            },
            text = {
                Text(text = "Are you sure wanna delete this food ?")
            },
            onDismissRequest = {
                !state.isConfirmDeleteDialogOpen
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(AddEditEvent.OnDeleteFood)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(AddEditEvent.OnDismissDialog)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    LaunchedEffect(key1 = state.token) {
        if (state.token.isEmpty()) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
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
                text = if (state.isEdit) {
                    "Edit Your Food"
                } else {
                    "Add New Food"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormImageTextField(
                label = "Food Display",
                description = "Enhance your store's appeal with our food display feature ",
                onImageCapture = {
                    onEvent(AddEditEvent.OnChangeFoodDisplayImage(it))
                },
                imageUri = state.foodDisplayImage,
                imageUrl = state.foodDisplayUrlImage
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.foodName,
                onValueChange = {
                    onEvent(AddEditEvent.OnChangeFoodName(it))
                },
                errorResult = state.foodNameValidation,
                label = "Food Name",
                description = "Name your culinary creations and make them stand out on MamMates",
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = if (state.foodPrice == 0) {
                    ""
                } else {
                    "${state.foodPrice}"
                },
                onValueChange = {
                    onEvent(AddEditEvent.OnChangeFoodPrice(it))
                },
                errorResult = state.foodPriceValidation,
                label = "Food Price (Rp)",
                description = "Input your desired prices and attract food enthusiasts on MamMates.",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (state.foodPriceSuggestion == null) {
                    "No Price Suggestion Provides"
                } else {
                    "Price Suggestion = Rp. ${state.foodPriceSuggestion}"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(20.dp))
            FormImageTextField(
                label = "MamRates",
                description = "Upload a photo for automatic MamRates. Once generated, ratings cannot be changed",
                onImageCapture = {
                    onEvent(AddEditEvent.OnChangeFoodMamRatesImage(it))
                },
                imageUri = state.foodMamRatesImage,
                validationText = state.foodMamRatesImageValidation,
                imageUrl = state.foodMamRatesUrlImage
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
                FilledTonalButton(onClick = {
                    onEvent(AddEditEvent.OnGenerateMamRatesFromImage(context))
                }) {
                    Text(
                        text = if (state.isEdit) {
                            "Update"
                        } else {
                            "Generate"
                        }
                    )

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (!state.foodCategory.isNullOrEmpty()) {
                    "Category : ${state.foodCategory}"
                } else {
                    "Please generate your Rating !"
                },
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
                    onClick = {
                        navController.navigate(NavigationRoutes.Main.ReportMamRates.route)
                    }
                ) {
                    Text(
                        text = "Reports",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            if (state.isEdit) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        onEvent(AddEditEvent.OnOpenDeleteDialog)
                    }
                ) {
                    Text(text = "Delete Food")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(AddEditEvent.OnUpdateAddFood(context))
                }
            ) {
                Text(
                    text = if (state.isEdit) {
                        "Save Changes"
                    } else {
                        "Add Food"
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AddEditScreen(
        navController = rememberNavController(),
        state = AddEditState(),
        onEvent = {}
    )
}
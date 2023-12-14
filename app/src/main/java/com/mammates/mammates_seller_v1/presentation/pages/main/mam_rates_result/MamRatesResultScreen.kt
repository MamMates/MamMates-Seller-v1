package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.rating.RatingDisplay
import com.mammates.mammates_seller_v1.presentation.component.text.TextLabelValue
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes
import com.mammates.mammates_seller_v1.util.Rating

@Composable
fun MamRatesResultScreen(
    navController: NavController,
    state: MamRatesResultState,
    onEvent: (MamRatesResultEvent) -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.token) {
        if (state.token.isEmpty()) {
            navController.navigate(route = NavigationRoutes.Auth.route) {
                popUpTo(route = NavigationRoutes.Main.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.isNotAuthorizeDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Please Login")
            },
            text = {
                Text(
                    text = "You must login to continue !",
                    textAlign = TextAlign.Center
                )

            },
            onDismissRequest = {
                state.isNotAuthorizeDialogOpen
            },
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Alert Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(MamRatesResultEvent.OnDismissNotAuthorize)
                }) {
                    Text(text = "Login")

                }
            }
        )
    }

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
                    onEvent(MamRatesResultEvent.OnDismissDialog)
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
                    onEvent(MamRatesResultEvent.OnDismissDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
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
                .padding(35.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Your MamRates",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(15.dp))
            FormImageTextField(
                label = "Take Your Food Photo",
                description = "Capture the essence of your culinary masterpiece! Snap a photo of your delectable dish to generate MamRates – our AI-powered system that assesses and elevates your food's visual appeal and quality.",
                onImageCapture = {
                    onEvent(MamRatesResultEvent.OnCaptureImage(it))
                },
                imageUri = state.imageUri
            )
            Spacer(modifier = Modifier.height(15.dp))
            RatingDisplay(
                rating = state.rating,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (state.rating == Rating.Undefine) {
                    "Capture your culinary moments! Upload a photo to get MamRates and pay attention to the presentation and quality of your food now."
                } else {
                    "Congratulations! Your dish earned a ${state.rating.rating}-star MamRates, highlighting its visual appeal and quality. Keep impressing taste buds on MamMates!"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(25.dp))
            TextLabelValue(
                label = "Category:",
                value = state.category,
            )
            Spacer(modifier = Modifier.height(25.dp))
            TextLabelValue(
                label = "PriceSugestion:",
                value = if (state.price == -69) {
                    "No Price Provides"
                } else {
                    "Rp. ${state.price}"
                },
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "How to increase the MamRates ?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Elevating your MamRates is simple – consider infusing your dish with creative presentations or exciting flavors that captivate the senses. By enhancing the visual appeal and taste of your culinary creation, you can ensure a higher MamRates score and make a lasting impression on discerning customers. Embrace innovation and watch as your food becomes a standout star on MamMates",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(25.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(MamRatesResultEvent.OnGetMamRates(context = context))
                }
            ) {
                Text(text = "See Your MamRates")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    MamRatesResultScreen(
        navController = rememberNavController(),
        state = MamRatesResultState(),
        onEvent = {}
    )
}
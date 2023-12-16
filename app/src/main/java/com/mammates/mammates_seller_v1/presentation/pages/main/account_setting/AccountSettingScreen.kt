package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun AccountSettingScreen(
    navController: NavController,
    state: AccountSettingState,
    onEvent: (AccountSettingEvent) -> Unit
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()



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
                    onEvent(AccountSettingEvent.OnDismissDialog)
                    navController.navigate(NavigationRoutes.Main.Account.route) {
                        popUpTo(NavigationRoutes.Main.AccountSetting.route) {
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
                    onEvent(AccountSettingEvent.OnDismissDialog)
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }

    if (state.isConfirmDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Confirm the action")
            },
            text = {
                Text(text = "Are you sure wanna save this changes ?")
            },
            onDismissRequest = {
                !state.isConfirmDialogOpen
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(AccountSettingEvent.OnConfirmChangesAccount(context))
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(AccountSettingEvent.OnDismissDialog)
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
                text = "Account Setting",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormImageTextField(
                label = "Store Profile Picture",
                description = "Upload a captivating store profile picture that represents the heart and soul of your culinary offerings on MamMates.",
                onImageCapture = {
                    onEvent(AccountSettingEvent.OnChangeProfileImage(it))
                },
                imageUri = state.profileImageUri,
                imageUrl = state.profileImageUrl
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.storeName,
                onValueChange = {
                    onEvent(AccountSettingEvent.OnChangeStoreName(it))
                },
                errorResult = state.storeNameValidation,
                label = "Store Name",
                description = "Update your account seamlessly by providing your store's name in the designated section."
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.storeAddress,
                onValueChange = {
                    onEvent(AccountSettingEvent.OnChangeAddress(it))

                },
                errorResult = state.storeAddressValidation,
                label = "Store Address",
                description = "\n" +
                        "Effortlessly update your account by entering your store's address in the provided section."
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.name,
                onValueChange = {
                    onEvent(AccountSettingEvent.OnChangeName(it))

                },
                errorResult = state.nameValidation,
                label = "Full Name",
                description = "Update your account effortlessly by entering your name in the designated section."
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.email,
                onValueChange = {
                    onEvent(AccountSettingEvent.OnChangeEmail(it))

                },
                errorResult = state.emailValidation,
                label = "Email",
                description = "Seamlessly update your account by entering your email in the provided section."
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(AccountSettingEvent.OnOpenConfirmDialog)
                }) {
                Text(text = "Save Update")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    AccountSettingScreen(
        navController = rememberNavController(),
        state = AccountSettingState(),
        onEvent = {}
    )
}
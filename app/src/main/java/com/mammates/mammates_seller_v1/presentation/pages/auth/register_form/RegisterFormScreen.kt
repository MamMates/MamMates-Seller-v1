package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField
import com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.component.RegisterFormPasswordTextField
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun RegisterFormScreen(
    navController: NavController,
    state: RegisterFormState,
    onEvent: (RegisterFormEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    if (!state.errorMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Register Failed")
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
                Icon(Icons.Default.Info, contentDescription = "Alert Dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(RegisterFormEvent.OnDismissDialogError)
                }) {
                    Text(text = "Okay")

                }
            }
        )
    }

    if (!state.successMessage.isNullOrEmpty()) {
        AlertDialog(
            title = {
                Text(text = "Register Success")
            },
            text = {
                Text(
                    text = "",
                    textAlign = TextAlign.Center
                )

            },
            onDismissRequest = {
                state.successMessage.isEmpty()
            },
            icon = {
                Icon(Icons.Default.CheckCircle, contentDescription = "Alert Success")
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(RegisterFormEvent.OnDismissDialogSuccess)
                    navController.popBackStack(
                        route = NavigationRoutes.Auth.Login.route,
                        inclusive = false
                    )
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
                .verticalScroll(scrollState)
                .padding(horizontal = 35.dp),

            ) {
            Text(
                text = "Register Form",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.storeName,
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangeStoreName(it))
                },
                errorResult = state.storeNameValidationResult,
                label = "Store Name",
                description = "Craft a unique identity for your culinary venture – input your store name and let it shine on MamMates",
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.address,
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangeStoreAddress(it))
                },
                errorResult = state.addressValidationResult,
                label = "Store Address",
                description = "Pin your delicious location on the map – provide your store address for a spot-on culinary destination",
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.name,
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangeName(it))
                },
                errorResult = state.nameValidationResult,
                label = "Full Name",
                description = "Personalize your presence – share your full name and let customers know the face behind the fantastic flavors.",
            )
            Spacer(modifier = Modifier.height(20.dp))
            FormTextField(
                value = state.email,
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangeEmail(it))
                },
                errorResult = state.emailValidationResult,
                label = "Email",
                description = "Stay connected and informed – input your email for updates and opportunities to showcase your culinary delights.",
            )
            Spacer(modifier = Modifier.height(20.dp))
            RegisterFormPasswordTextField(
                description = "Guard your culinary secrets – create a secure password to protect your account and creations.",
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangePassword(it))
                },
                label = "Password",
                value = state.password,
                errorResult = state.passwordValidationResult,
                isPasswordVisible = state.isPasswordVisible,
                togglePasswordVisible = {
                    onEvent(RegisterFormEvent.OnTogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            RegisterFormPasswordTextField(
                description = "Double-check your secret recipe – confirm your password to ensure a seamless and secure culinary journey on MamMates.",
                onValueChange = {
                    onEvent(RegisterFormEvent.OnChangeConfirmPassword(it))
                },
                label = "Confirm Password",
                value = state.passwordConfirm,
                errorResult = state.passwordConfirmValidationResult,
                isPasswordVisible = state.isPasswordConfirmVisible,
                togglePasswordVisible = {
                    onEvent(RegisterFormEvent.OnTogglePasswordConfirmVisibility)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(RegisterFormEvent.OnRegister)
                }) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormScreenPreview() {
    RegisterFormScreen(
        navController = rememberNavController(),
        state = RegisterFormState(),
        onEvent = {}
    )
}
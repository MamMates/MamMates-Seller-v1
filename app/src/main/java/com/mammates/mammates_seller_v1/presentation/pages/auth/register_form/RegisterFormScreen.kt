package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.component.RegisterFormPasswordTextField
import com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.component.RegisterFormTextField
import com.mammates.mammates_seller_v1.presentation.util.navigation.NavigationRoutes

@Composable
fun RegisterFormScreen(
    navController: NavController,
    state: RegisterFormState,
    onEvent: (RegisterFormEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp)
            .verticalScroll(scrollState),

        ) {
        Text(
            text = "Register Form",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(20.dp))
        RegisterFormTextField(
            value = state.storeName,
            onValueChange = {
                onEvent(RegisterFormEvent.OnChangeStoreName(it))
            },
            errorResult = state.storeNameValidationResult,
            label = "Store Name",
            description = "Craft a unique identity for your culinary venture – input your store name and let it shine on MamMates",
        )
        Spacer(modifier = Modifier.height(20.dp))
        RegisterFormTextField(
            value = state.address,
            onValueChange = {
                onEvent(RegisterFormEvent.OnChangeStoreAddress(it))
            },
            errorResult = state.addressValidationResult,
            label = "Store Address",
            description = "Pin your delicious location on the map – provide your store address for a spot-on culinary destination",
        )
        Spacer(modifier = Modifier.height(20.dp))
        RegisterFormTextField(
            value = state.name,
            onValueChange = {
                onEvent(RegisterFormEvent.OnChangeName(it))
            },
            errorResult = state.nameValidationResult,
            label = "Full Name",
            description = "Personalize your presence – share your full name and let customers know the face behind the fantastic flavors.",
        )
        Spacer(modifier = Modifier.height(20.dp))
        RegisterFormTextField(
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

                // TODO : create good navigation
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(NavigationRoutes.Auth.Login.route)
            }) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.height(40.dp))
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
package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormImageTextField
import com.mammates.mammates_seller_v1.presentation.component.text_field.FormTextField

@Composable
fun AccountSettingScreen(
    navController: NavController,
    state: AccountSettingState,
    onEvent: (AccountSettingEvent) -> Unit
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
            text = "Account Setting",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormImageTextField(
            label = "Store Profile Picture",
            description = "Upload a captivating store profile picture that represents the heart and soul of your culinary offerings on MamMates.",
            onImageCapture = {

            },
            imageUri = state.profileImage
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.storeName,
            onValueChange = {

            },
            errorResult = state.storeNameValidation,
            label = "Store Name",
            description = "blablabla"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.storeAddress,
            onValueChange = {

            },
            errorResult = state.storeAddressValidation,
            label = "Store Address",
            description = "blablabla"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.name,
            onValueChange = {

            },
            errorResult = state.nameValidation,
            label = "Full Name",
            description = "blablabla"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FormTextField(
            value = state.email,
            onValueChange = {

            },
            errorResult = state.emailValidation,
            label = "Email",
            description = "blablabla"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }) {
            Text(text = "Save Update")
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
package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

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
import com.mammates.mammates_seller_v1.presentation.component.text_field.PrimaryTextField

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent) -> Unit
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
            text = "Change Password",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "To change your password, please enter your current password along with the new one. This ensures the security of your account. Thank you for choosing MamMates!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryTextField(
            value = state.oldPassword,
            onValueChange = {},
            errorResult = state.oldPasswordValidation,
            label = "Old Password"
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryTextField(
            value = state.newPassword,
            onValueChange = {},
            errorResult = state.newPasswordValidation,
            label = "New Password"
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryTextField(
            value = state.newPasswordConfirm,
            onValueChange = {},
            errorResult = state.newPasswordConfirmValidation,
            label = "Confirm New Password"
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Change")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    ChangePasswordScreen(
        navController = rememberNavController(),
        state = ChangePasswordState(),
        onEvent = {}
    )
}
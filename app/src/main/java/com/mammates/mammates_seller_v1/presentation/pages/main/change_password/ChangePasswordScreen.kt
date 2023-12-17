package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammates.mammates_seller_v1.presentation.component.dialog.ConfirmDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.ErrorDialog
import com.mammates.mammates_seller_v1.presentation.component.dialog.SuccesDialog
import com.mammates.mammates_seller_v1.presentation.component.loading.LoadingScreen
import com.mammates.mammates_seller_v1.presentation.component.text_field.PasswordTextField
import com.mammates.mammates_seller_v1.util.HttpError

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    if (state.isNotAuthorizeDialogOpen) {
        ErrorDialog(
            message = HttpError.UNAUTHORIZED.message,
            onConfirm = {
                onEvent(ChangePasswordEvent.OnDismissNotAuthorize)
            }
        )
    }


    if (state.isConfirmDialogOpen) {
        ConfirmDialog(
            message = "Are you sure wanna save this changes ?",
            onConfirm = {
                onEvent(ChangePasswordEvent.OnSubmitChangePassword)
            },
            onDismiss = {
                onEvent(ChangePasswordEvent.OnDismissDialog)
            }
        )
    }

    if (!state.errorMessage.isNullOrEmpty()) {
        ErrorDialog(
            message = state.errorMessage,
            onConfirm = {
                onEvent(ChangePasswordEvent.OnDismissDialog)
            }
        )
    }
    if (!state.successMessage.isNullOrEmpty()) {
        SuccesDialog(
            message = state.successMessage,
            onConfirm = {
                onEvent(ChangePasswordEvent.OnDismissDialog)
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
            PasswordTextField(
                value = state.oldPassword,
                onValueChange = {
                    onEvent(ChangePasswordEvent.OnChangeOldPassword(it))
                },
                errorResult = state.oldPasswordValidation,
                label = "Old Password",
                isPasswordVisible = state.oldPasswordVisible,
                togglePasswordVisible = {
                    onEvent(ChangePasswordEvent.OnToggleOldPasswordVisible)

                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                value = state.newPassword,
                onValueChange = {
                    onEvent(ChangePasswordEvent.OnChangeNewPassword(it))

                },
                errorResult = state.newPasswordValidation,
                label = "New Password",
                isPasswordVisible = state.newPasswordVisible,
                togglePasswordVisible = {
                    onEvent(ChangePasswordEvent.OnToggleNewPasswordVisible)

                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                value = state.newPasswordConfirm,
                onValueChange = {
                    onEvent(ChangePasswordEvent.OnChangeNewPasswordConfirm(it))
                },
                errorResult = state.newPasswordConfirmValidation,
                label = "Confirm New Password",
                isPasswordVisible = state.newPasswordConfirmVisible,
                togglePasswordVisible = {
                    onEvent(ChangePasswordEvent.OnToggleNewPasswordConfirmVisible)

                },
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(ChangePasswordEvent.OnOpenConfirmDialog)
                }
            ) {
                Text(text = "Change")
            }
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
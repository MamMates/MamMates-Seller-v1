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
import com.mammates.mammates_seller_v1.presentation.component.text_field.PasswordTextField
import com.mammates.mammates_seller_v1.presentation.util.loading.LoadingAnimation

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent) -> Unit
) {

    val scrollState = rememberScrollState()

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
                    onEvent(ChangePasswordEvent.OnDismissNotAuthorize)
                }) {
                    Text(text = "Login")

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
                        onEvent(ChangePasswordEvent.OnSubmitChangePassword)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(ChangePasswordEvent.OnDismissDialog)
                    }
                ) {
                    Text("Dismiss")
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
                    onEvent(ChangePasswordEvent.OnDismissDialog)
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
                    onEvent(ChangePasswordEvent.OnDismissDialog)
                    navController.popBackStack()
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
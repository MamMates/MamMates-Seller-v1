package com.mammates.mammates_seller_v1.presentation.component.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    onValueChange: (String) -> Unit,
    label: String,
    value: String,
    errorResult: String?,
    isPasswordVisible: Boolean,
    togglePasswordVisible: (() -> Unit)
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = if (value.isEmpty()) {
            { Text(text = label) }
        } else {
            null
        },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = errorResult != null,
        supportingText = {
            errorResult?.let {
                Text(text = it)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.background
        ),
        trailingIcon = {
            IconButton(
                onClick = togglePasswordVisible
            ) {
                Icon(
                    imageVector = if (isPasswordVisible) {
                        Icons.Outlined.VisibilityOff
                    } else {
                        Icons.Outlined.Visibility
                    },
                    contentDescription = "Password visibility"
                )
            }
        },
    )
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    PasswordTextField(
        onValueChange = {},
        label = "Password",
        value = "Ehe",
        errorResult = null,
        isPasswordVisible = true,
        togglePasswordVisible = {

        }
    )
}
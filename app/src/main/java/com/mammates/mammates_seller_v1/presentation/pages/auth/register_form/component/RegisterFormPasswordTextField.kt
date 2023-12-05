package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.presentation.component.text_field.PasswordTextField


@Composable
fun RegisterFormPasswordTextField(
    description: String,
    onValueChange: (String) -> Unit,
    label: String,
    value: String,
    errorResult: String?,
    isPasswordVisible: Boolean,
    togglePasswordVisible: (() -> Unit)
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(25.dp))
        PasswordTextField(
            onValueChange = onValueChange,
            label = label,
            value = value,
            errorResult = errorResult,
            isPasswordVisible = isPasswordVisible,
            togglePasswordVisible = togglePasswordVisible
        )
    }
}
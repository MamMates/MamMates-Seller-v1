package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.presentation.component.text_field.PrimaryTextField


@Composable
fun RegisterFormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorResult: String?,
    label: String,
    description: String,
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
        PrimaryTextField(
            value = value,
            onValueChange = onValueChange,
            errorResult = errorResult,
            label = label
        )
    }
}
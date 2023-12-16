package com.mammates.mammates_seller_v1.presentation.component.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorResult: String?,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = if (value.isEmpty()) {
            { Text(text = label) }
        } else {
            null
        },
        isError = errorResult != null,
        supportingText = {
            errorResult?.let {
                Text(text = it)
            }
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.background,
        ),
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
fun PrimaryTextFieldPreview() {
    PrimaryTextField(
        value = "",
        onValueChange = {},
        errorResult = null,
        label = "Store Name"
    )
}
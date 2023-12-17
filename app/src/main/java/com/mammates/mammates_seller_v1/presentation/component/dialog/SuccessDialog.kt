package com.mammates.mammates_seller_v1.presentation.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SuccesDialog(
    message: String,
    onConfirm: () -> Unit,
    title: String = "Success!"
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(
                text = message,
                textAlign = TextAlign.Center
            )

        },
        onDismissRequest = {},
        icon = {
            Icon(Icons.Default.CheckCircle, contentDescription = "Alert Success")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Okay")

            }
        }
    )
}
package com.mammates.mammates_seller_v1.presentation.pages.main.order_detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.util.StatusOrder

@Composable
fun TextLabelValue(
    label: String,
    value: String? = null,
    statusOrder: StatusOrder? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        if (statusOrder == null) {
            Text(
                text = value ?: "",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )
        } else {
            Button(
                modifier = Modifier.defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = 10.dp
                ),
                onClick = { },
                enabled = false,
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 1.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = statusOrder.color,
                    disabledContentColor = Color.White
                )

            ) {
                Text(
                    text = statusOrder.name,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
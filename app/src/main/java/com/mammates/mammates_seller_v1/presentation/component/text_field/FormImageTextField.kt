package com.mammates.mammates_seller_v1.presentation.component.text_field

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.R

@Composable
fun FormImageTextField(
    modifier: Modifier = Modifier,
    label: String,
    description: String,
    image: ImageBitmap? = null,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(135.dp)
                    .height(135.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {

                    }
            ) {
                Image(
                    modifier = Modifier
                        .width(135.dp)
                        .height(135.dp),
                    bitmap = image ?: BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.image_placeholder
                    ).asImageBitmap(),
                    contentDescription = "Food Display"
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormImageTextFieldPreview() {
    FormImageTextField(

        label = "Food Display",
        description = "Enhance your store's appeal with our food display feature"
    )
}
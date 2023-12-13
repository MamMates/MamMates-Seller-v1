package com.mammates.mammates_seller_v1.presentation.component.text_field

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.util.createImageFile
import java.util.Objects

@Composable
fun FormImageTextField(
    modifier: Modifier = Modifier,
    label: String,
    description: String,
    onImageCapture: (Uri) -> Unit,
    imageUri: Uri?,
    imageUrl: String? = null,
    validationText: String? = null,
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",
        file
    )


    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = {
                onImageCapture(uri)
            }
        )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT)
                    .show()
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )



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
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }

                    }
            ) {
                if (imageUri != Uri.EMPTY) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        modifier = Modifier
                            .width(135.dp)
                            .height(135.dp),
                        contentDescription = "Image Display",
                        contentScale = ContentScale.Crop
                    )
                } else if (!imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        modifier = Modifier
                            .width(135.dp)
                            .height(135.dp),
                        model = imageUrl,
                        contentDescription = "",
                        placeholder = painterResource(
                            id = R.drawable.image_placeholder
                        ),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.image_placeholder),
                        modifier = Modifier
                            .width(135.dp)
                            .height(135.dp),
                        contentDescription = "Image Display",
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        if (!validationText.isNullOrEmpty()) {
            Text(
                text = validationText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormImageTextFieldPreview() {
    FormImageTextField(
        label = "Food Display",
        description = "Enhance your store's appeal with our food display feature",
        onImageCapture = {},
        imageUri = Uri.EMPTY
    )
}
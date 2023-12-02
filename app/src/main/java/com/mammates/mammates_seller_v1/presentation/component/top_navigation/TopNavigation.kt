package com.mammates.mammates_seller_v1.presentation.component.top_navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigation() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(text = "MamMates")
        },
        navigationIcon = {
                Icon(
                    modifier = Modifier
                        .height(30.dp)
                        .padding(horizontal = 20.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )


        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopNavigationPreview() {
    TopNavigation()
}
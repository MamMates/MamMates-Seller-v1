package com.mammates.mammates_seller_v1.presentation.pages.main.result

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.mammates.mammates_seller_v1.R
import com.mammates.mammates_seller_v1.util.Rating

data class ResultState(
    val rating: Rating = Rating.ZERO,
    val category: String = "",
    val image : ImageBitmap? = null
)

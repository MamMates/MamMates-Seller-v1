package com.mammates.mammates_seller_v1.util

import androidx.compose.ui.graphics.Color

enum class StatusOrder(val color: Color) {
    Unconfirmed(
        color = Color(0xFFFFB80E)
    ),
    Confirmed(
        color = Color(0xFF00B8EA)
    ),
    Finish(
        color = Color(0xFF008027)
    ),
    Canceled(
        color = Color(0xFFDF0000)
    ),
    Unidentified(
        color = Color(0xFF444444)
    )

}
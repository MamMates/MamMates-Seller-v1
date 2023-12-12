package com.mammates.mammates_seller_v1.util

import androidx.compose.ui.graphics.Color

enum class StatusOrder(val color: Color, val statusNumber: Int) {
    Unconfirmed(
        color = Color(0xFFFFB80E),
        statusNumber = 1,
    ),
    Confirmed(
        color = Color(0xFF00B8EA),
        statusNumber = 2,
    ),
    Finish(
        color = Color(0xFF008027),
        statusNumber = 3,

        ),
    Canceled(
        color = Color(0xFFDF0000),
        statusNumber = 0,

        ),
    Unidentified(
        color = Color(0xFF444444),
        statusNumber = -9,
    )

}
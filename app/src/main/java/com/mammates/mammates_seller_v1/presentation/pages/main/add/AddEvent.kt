package com.mammates.mammates_seller_v1.presentation.pages.main.add

import android.net.Uri

sealed class AddEvent {
    data class OnChangeFoodDisplayImage(val uri: Uri) : AddEvent()
}
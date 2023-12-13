package com.mammates.mammates_seller_v1.presentation.pages.main.add

import android.content.Context
import android.net.Uri

sealed class AddEvent {
    data class OnChangeFoodDisplayImage(val uri: Uri) : AddEvent()
    data class OnChangeFoodName(val name: String) : AddEvent()
    data class OnChangeFoodPrice(val price: String) : AddEvent()
    data class OnChangeFoodMamRatesImage(val uri: Uri) : AddEvent()
    data class OnGenerateMamRatesFromImage(val context: Context) : AddEvent()
    data class OnUpdateAddFood(val context: Context) : AddEvent()
}
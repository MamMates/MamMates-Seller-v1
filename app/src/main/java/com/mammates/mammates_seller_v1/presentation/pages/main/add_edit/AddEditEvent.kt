package com.mammates.mammates_seller_v1.presentation.pages.main.add_edit

import android.content.Context
import android.net.Uri

sealed class AddEditEvent {
    data class OnChangeFoodDisplayImage(val uri: Uri) : AddEditEvent()
    data class OnChangeFoodName(val name: String) : AddEditEvent()
    data class OnChangeFoodPrice(val price: String) : AddEditEvent()
    data class OnChangeFoodMamRatesImage(val uri: Uri) : AddEditEvent()
    data class OnGenerateMamRatesFromImage(val context: Context) : AddEditEvent()
    data class OnUpdateAddFood(val context: Context) : AddEditEvent()
    data object OnDismissDialog : AddEditEvent()
    data object OnDeleteFood : AddEditEvent()
    data object OnOpenDeleteDialog : AddEditEvent()
    data object OnDismissNotAuthorize : AddEditEvent()
}
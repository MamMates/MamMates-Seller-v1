package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result

import android.content.Context
import android.net.Uri

sealed class MamRatesResultEvent {
    data class OnCaptureImage(val uri: Uri) : MamRatesResultEvent()
    data object OnDismissDialog : MamRatesResultEvent()
    data class OnGetMamRates(val context: Context) : MamRatesResultEvent()
    data object OnDismissNotAuthorize : MamRatesResultEvent()
}
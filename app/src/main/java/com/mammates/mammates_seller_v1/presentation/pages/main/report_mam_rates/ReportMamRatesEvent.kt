package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import android.content.Context
import android.net.Uri

sealed class ReportMamRatesEvent {
    data object OnDismissDialog : ReportMamRatesEvent()
    data object OnDismissNotAuthorize : ReportMamRatesEvent()
    data class OnChangeFoodName(val foodName: String) : ReportMamRatesEvent()
    data class OnChangeFoodPrice(val foodPrice: String) : ReportMamRatesEvent()
    data class OnChangeRating(val rating: String) : ReportMamRatesEvent()
    data class OnChangeFoodImageUri(val uri: Uri) : ReportMamRatesEvent()
    data class OnSubmitReport(val context: Context) : ReportMamRatesEvent()
}
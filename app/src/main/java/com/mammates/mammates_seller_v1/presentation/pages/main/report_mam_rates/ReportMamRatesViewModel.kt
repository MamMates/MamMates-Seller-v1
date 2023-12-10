package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReportMamRatesViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(ReportMamRatesState())
    val state = _state.asStateFlow()

    fun onEvent(event: ReportMamRatesEvent) {

    }

}
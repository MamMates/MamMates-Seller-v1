package com.mammates.mammates_seller_v1.presentation.pages.mam_rates

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MamRatesViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(MamRatesState())
    val state = _state.asStateFlow()

    fun onEvent(event: MamRatesEvent) {

    }

}
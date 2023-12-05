package com.mammates.mammates_seller_v1.presentation.pages.main.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state = _state.asStateFlow()

    fun onEvent(event: OrderEvent) {

    }
}
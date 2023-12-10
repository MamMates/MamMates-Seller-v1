package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChangePasswordEvent) {

    }

}
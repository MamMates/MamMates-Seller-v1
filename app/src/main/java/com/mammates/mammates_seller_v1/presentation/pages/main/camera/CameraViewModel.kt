package com.mammates.mammates_seller_v1.presentation.pages.main.camera

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onEvent(event: CameraEvent) {

    }

}
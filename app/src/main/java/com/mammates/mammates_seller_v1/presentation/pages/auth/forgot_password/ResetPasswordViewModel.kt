package com.mammates.mammates_seller_v1.presentation.pages.auth.forgot_password

import androidx.lifecycle.ViewModel
import com.mammates.mammates_seller_v1.presentation.util.validation.emailValidation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.OnChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    emailValidationResult = emailValidation(event.email)
                )
            }
        }
    }
}
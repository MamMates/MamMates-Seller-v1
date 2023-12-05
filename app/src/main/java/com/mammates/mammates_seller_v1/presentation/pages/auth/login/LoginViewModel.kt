package com.mammates.mammates_seller_v1.presentation.pages.auth.login

import androidx.lifecycle.ViewModel
import com.mammates.mammates_seller_v1.presentation.util.validation.emailValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    emailValidationResult = emailValidation(event.email)
                )
            }

            is LoginEvent.OnChangePassword -> {
                _state.value = _state.value.copy(
                    password = event.password,
                    passwordValidationResult = passwordValidation(event.password)
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
        }
    }


}
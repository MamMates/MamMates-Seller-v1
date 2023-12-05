package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

import androidx.lifecycle.ViewModel
import com.mammates.mammates_seller_v1.presentation.util.validation.emailValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(RegisterFormState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.OnChangeStoreName -> {
                _state.value = _state.value.copy(
                    storeName = event.storeName,
                    storeNameValidationResult = emptyValidation(
                        label = "Store Name",
                        value = event.storeName
                    )
                )
            }

            is RegisterFormEvent.OnChangeStoreAddress -> {
                _state.value = _state.value.copy(
                    address = event.address,
                    addressValidationResult = emptyValidation(
                        label = "Store Address",
                        value = event.address
                    )
                )
            }

            is RegisterFormEvent.OnChangeName -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    nameValidationResult = emptyValidation(
                        label = "Name",
                        value = event.name
                    )
                )
            }

            is RegisterFormEvent.OnChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    emailValidationResult = emailValidation(
                        event.email
                    )
                )
            }

            is RegisterFormEvent.OnChangePassword -> {
                _state.value = _state.value.copy(
                    password = event.password,
                    passwordValidationResult = passwordValidation(
                        event.password
                    )
                )
            }

            is RegisterFormEvent.OnChangeConfirmPassword -> {
                _state.value = _state.value.copy(
                    passwordConfirm = event.confirmPassword,
                    passwordConfirmValidationResult = if (_state.value.password != _state.value.passwordConfirm) {
                        "Password doesn't match"
                    } else {
                        passwordValidation(event.confirmPassword)
                    }
                )
            }

            RegisterFormEvent.OnTogglePasswordConfirmVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordConfirmVisible = !_state.value.isPasswordConfirmVisible
                )
            }

            RegisterFormEvent.OnTogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
        }
    }


}
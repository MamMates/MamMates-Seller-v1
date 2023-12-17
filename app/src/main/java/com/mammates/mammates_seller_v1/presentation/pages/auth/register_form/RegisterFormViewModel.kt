package com.mammates.mammates_seller_v1.presentation.pages.auth.register_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.auth.AuthUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.emailValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
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
                    passwordConfirmValidationResult = if (_state.value.password != event.confirmPassword) {
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

            RegisterFormEvent.OnRegister -> {

                validateAllFieldValue()

                if (
                    !_state.value.storeNameValidationResult.isNullOrEmpty() &&
                    !_state.value.addressValidationResult.isNullOrEmpty() &&
                    !_state.value.nameValidationResult.isNullOrEmpty() &&
                    !_state.value.emailValidationResult.isNullOrEmpty() &&
                    !_state.value.passwordValidationResult.isNullOrEmpty() &&
                    !_state.value.passwordConfirmValidationResult.isNullOrEmpty()
                ) {
                    return
                }

                registerUser()

            }

            RegisterFormEvent.OnDismissDialogError -> {
                _state.value = _state.value.copy(
                    errorMessage = null
                )
            }

            RegisterFormEvent.OnDismissDialogSuccess -> {
                _state.value = _state.value.copy(
                    successMessage = null
                )
            }
        }
    }

    private fun validateAllFieldValue() {
        _state.value = _state.value.copy(
            storeNameValidationResult = emptyValidation(
                label = "Store Name",
                value = _state.value.storeName
            ),
            addressValidationResult = emptyValidation(
                label = "Store Address",
                value = _state.value.address
            ),
            nameValidationResult = emptyValidation(
                label = "Name",
                value = _state.value.name
            ),
            emailValidationResult = emailValidation(
                _state.value.email
            ),
            passwordValidationResult = passwordValidation(
                _state.value.password
            ),
            passwordConfirmValidationResult = if (_state.value.password != _state.value.passwordConfirm) {
                "Password doesn't match"
            } else {
                passwordValidation(_state.value.passwordConfirm)
            }
        )
    }

    private fun registerUser() {
        authUseCases.authRegisterUseCase(
            email = _state.value.email,
            store = _state.value.storeName,
            address = _state.value.address,
            seller = _state.value.name,
            password = _state.value.password,
            passwordConfirm = _state.value.passwordConfirm
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = result.message,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}
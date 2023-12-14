package com.mammates.mammates_seller_v1.presentation.pages.main.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.account.AccountUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.OnChangeNewPassword -> {
                _state.value = _state.value.copy(
                    newPassword = event.password,
                    newPasswordValidation = passwordValidation(event.password)
                )
            }

            is ChangePasswordEvent.OnChangeNewPasswordConfirm -> {
                _state.value = _state.value.copy(
                    newPasswordConfirm = event.password,
                    newPasswordConfirmValidation = if (_state.value.newPassword != event.password) {
                        "Password doesn't match"
                    } else {
                        passwordValidation(event.password)
                    }
                )
            }

            is ChangePasswordEvent.OnChangeOldPassword -> {
                _state.value = _state.value.copy(
                    oldPassword = event.password,
                    oldPasswordValidation = passwordValidation(event.password)
                )
            }

            ChangePasswordEvent.OnToggleNewPasswordConfirmVisible -> {
                _state.value = _state.value.copy(
                    newPasswordConfirmVisible = !_state.value.newPasswordConfirmVisible
                )
            }

            ChangePasswordEvent.OnToggleNewPasswordVisible -> {
                _state.value = _state.value.copy(
                    newPasswordVisible = !_state.value.newPasswordVisible
                )
            }

            ChangePasswordEvent.OnToggleOldPasswordVisible -> {
                _state.value = _state.value.copy(
                    oldPasswordVisible = !_state.value.oldPasswordVisible
                )
            }

            ChangePasswordEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                    isConfirmDialogOpen = false,
                )
            }

            ChangePasswordEvent.OnSubmitChangePassword -> {
                validateAllFieldValue()
                if (
                    !_state.value.oldPasswordValidation.isNullOrEmpty() &&
                    !_state.value.newPasswordValidation.isNullOrEmpty() &&
                    !_state.value.newPasswordConfirmValidation.isNullOrEmpty()
                ) {
                    return
                }

                changePassword()

            }

            ChangePasswordEvent.OnOpenConfirmDialog -> {
                _state.value = _state.value.copy(
                    isConfirmDialogOpen = true
                )
            }

            ChangePasswordEvent.OnDismissNotAuthorize -> {
                viewModelScope.launch {
                    tokenUseCases.clearTokenUseCase()
                }
                _state.value = _state.value.copy(
                    token = ""
                )
            }
        }
    }

    private fun getTokenValue() {
        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )
    }

    private fun validateAllFieldValue() {
        _state.value = _state.value.copy(
            newPasswordValidation = passwordValidation(_state.value.newPassword),
            newPasswordConfirmValidation = if (_state.value.newPassword != _state.value.newPasswordConfirm) {
                "Password doesn't match"
            } else {
                passwordValidation(_state.value.newPasswordConfirm)
            },
            oldPasswordValidation = passwordValidation(_state.value.oldPassword)
        )
    }

    private fun changePassword() {
        accountUseCases.changePasswordUseCase(
            token = _state.value.token,
            oldPassword = _state.value.oldPassword,
            newPassword = _state.value.newPassword,
            newPasswordConfirm = _state.value.newPasswordConfirm
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.message.equals("401")) {
                        _state.value = _state.value.copy(
                            isNotAuthorizeDialogOpen = true,
                            isLoading = false,
                        )
                        return@onEach
                    }
                    _state.value = _state.value.copy(
                        errorMessage = result.message,
                        isLoading = false
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
                        isLoading = false,
                        isConfirmDialogOpen = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}
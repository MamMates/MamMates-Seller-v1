package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.account.AccountUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.emailValidation
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.ImageUtils
import com.mammates.mammates_seller_v1.util.ImageUtils.Companion.reduceFileImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class AccountSettingViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val accountUseCases: AccountUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(AccountSettingState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
        getInitialDataAccount()
    }


    fun onEvent(event: AccountSettingEvent) {
        when (event) {
            is AccountSettingEvent.OnChangeAddress -> {
                _state.value = _state.value.copy(
                    storeAddress = event.address,
                    storeAddressValidation = emptyValidation(event.address, "Store Address")
                )
            }

            is AccountSettingEvent.OnChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    emailValidation = emailValidation(event.email)
                )
            }

            is AccountSettingEvent.OnChangeName -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    nameValidation = emptyValidation(event.name, "Name")
                )
            }

            is AccountSettingEvent.OnChangeProfileImage -> {
                _state.value = _state.value.copy(
                    profileImageUri = event.uri
                )
            }

            is AccountSettingEvent.OnChangeStoreName -> {
                _state.value = _state.value.copy(
                    storeName = event.storeName,
                    storeNameValidation = emptyValidation(event.storeName, "Name")
                )
            }

            AccountSettingEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                    isConfirmDialogOpen = false
                )
            }

            AccountSettingEvent.OnOpenConfirmDialog -> {
                _state.value = _state.value.copy(
                    isConfirmDialogOpen = true
                )
            }

            is AccountSettingEvent.OnConfirmChangesAccount -> {
                validateAllFieldValue()

                if (
                    !_state.value.nameValidation.isNullOrEmpty() &&
                    !_state.value.storeAddressValidation.isNullOrEmpty() &&
                    !_state.value.emailValidation.isNullOrEmpty() &&
                    !_state.value.storeNameValidation.isNullOrEmpty()
                ) {
                    return
                }

                if (_state.value.profileImageUri != Uri.EMPTY) {
                    patchImageProfile(event.context)
                }

                saveChangeAccount()

                if (!_state.value.errorMessage.isNullOrEmpty() &&
                    !_state.value.successMessage.isNullOrEmpty()
                ) {
                    _state.value = _state.value.copy(
                        successMessage = null,
                        isConfirmDialogOpen = false,
                    )
                    return
                }

                _state.value = _state.value.copy(
                    isConfirmDialogOpen = false
                )
            }

            AccountSettingEvent.ClearToken -> {
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
            storeAddressValidation = emptyValidation(_state.value.storeAddress, "Store Address"),
            emailValidation = emailValidation(_state.value.email),
            nameValidation = emptyValidation(_state.value.name, "Name"),
            storeNameValidation = emptyValidation(_state.value.storeName, "Name")
        )
    }

    private fun getInitialDataAccount() {
        accountUseCases.getAccountUseCase(_state.value.token).onEach { result ->
            when (result) {
                is Resource.Error -> {

                    if (result.message.equals(HttpError.UNAUTHORIZED.message)) {
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
                    result.data?.let { data ->
                        _state.value = _state.value.copy(
                            storeName = data.store ?: "",
                            storeAddress = data.address ?: "",
                            name = data.seller ?: "",
                            email = data.email ?: "",
                            profileImageUrl = data.image,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveChangeAccount() {
        accountUseCases.updateAccountUseCase(
            token = _state.value.token,
            store = _state.value.storeName,
            seller = _state.value.name,
            address = _state.value.storeAddress,
            email = _state.value.email
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
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
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun patchImageProfile(context: Context) {
        val imgFile = ImageUtils.uriToFile(_state.value.profileImageUri, context).reduceFileImage()

        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imgFile.name,
            imgFile.asRequestBody("image/jpeg".toMediaType())
        )

        accountUseCases.updateProfilePictureUseCase(
            _state.value.token, multipartBody
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
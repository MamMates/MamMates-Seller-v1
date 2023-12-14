package com.mammates.mammates_seller_v1.presentation.pages.main.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.account.AccountUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val accountUseCases: AccountUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.OnLogout -> {
                viewModelScope.launch {
                    tokenUseCases.clearTokenUseCase()
                }
                _state.value = _state.value.copy(
                    token = ""
                )
            }

            AccountEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null
                )
            }

            AccountEvent.OnRefreshPage -> {
                getTokenValue()
                getStoreData()
            }
        }
    }

    private fun getTokenValue() {
        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )
    }

    private fun getStoreData() {
        accountUseCases.getStoreUseCase(_state.value.token).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = result.message,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    result.data?.let { data ->
                        _state.value = _state.value.copy(
                            storeName = data.name ?: "No Data Store Name",
                            storeImage = data.image,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
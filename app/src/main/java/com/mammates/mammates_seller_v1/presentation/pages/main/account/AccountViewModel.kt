package com.mammates.mammates_seller_v1.presentation.pages.main.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            isAuth = tokenUseCases.getTokenUseCase().isNotEmpty()
        )
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.OnLogout -> {
                viewModelScope.launch {
                    tokenUseCases.clearTokenUseCase()
                    _state.value = _state.value.copy(
                        isAuth = false
                    )
                }
            }
        }
    }
}
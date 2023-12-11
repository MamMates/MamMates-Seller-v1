package com.mammates.mammates_seller_v1.presentation.pages.main.store

import androidx.lifecycle.ViewModel
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(StoreState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            isAuth = tokenUseCases.getTokenUseCase().isNotEmpty()
        )
    }

    fun onEvent(event: StoreEvent) {

    }
}
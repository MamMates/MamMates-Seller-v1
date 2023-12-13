package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates

import androidx.lifecycle.ViewModel
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MamRatesViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MamRatesState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )
    }

    fun onEvent(event: MamRatesEvent) {

    }

}
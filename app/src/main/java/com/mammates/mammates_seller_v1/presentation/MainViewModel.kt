package com.mammates.mammates_seller_v1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.domain.use_case.intro.IntroUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val introUseCases: IntroUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        introUseCases.getIntroIsDoneUseCase().onEach { isDone ->
            _state.value = _state.value.copy(
                isOnBoarding = isDone
            )
        }.launchIn(viewModelScope)
    }
}
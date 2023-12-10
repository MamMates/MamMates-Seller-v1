package com.mammates.mammates_seller_v1.presentation.pages.on_boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.domain.use_case.intro.IntroUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val introUseCases: IntroUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(OnBoardingState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.SetIntroIsDone -> {
                viewModelScope.launch {
                    try {
                        introUseCases.setIntroIsDoneUseCase(isDone = true)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            errorMessage = e.message
                        )
                    }
                }
            }
        }
    }
}
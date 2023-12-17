package com.mammates.mammates_seller_v1.presentation.pages.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.intro.IntroUseCases
import com.mammates.mammates_seller_v1.domain.use_case.order.OrderUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.util.HttpError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val introUseCases: IntroUseCases,
    private val tokenUseCases: TokenUseCases,
    private val ordersUseCase: OrderUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {

        getInitialValue()


    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnDismissErrorDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                )
            }

            HomeEvent.OnRefreshPage -> {
                getInitialValue()
                getRecentOrder()
            }

            HomeEvent.ClearToken -> {
                viewModelScope.launch {
                    tokenUseCases.clearTokenUseCase()
                }
                _state.value = _state.value.copy(
                    token = ""
                )
            }
        }
    }

    private fun getInitialValue() {
        _state.value = _state.value.copy(
            isOnBoarding = introUseCases.getIntroIsDoneUseCase(),
            token = tokenUseCases.getTokenUseCase()
        )
    }

    private fun getRecentOrder() {
        ordersUseCase.getOrderRecentUseCase(
            token = _state.value.token,
        ).onEach { result ->
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
                    _state.value = _state.value.copy(
                        orderList = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
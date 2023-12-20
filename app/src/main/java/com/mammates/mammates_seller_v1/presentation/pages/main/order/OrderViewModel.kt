package com.mammates.mammates_seller_v1.presentation.pages.main.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.order.OrderUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.StatusOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val orderUseCases: OrderUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OnChangeTab -> {
                _state.value = _state.value.copy(
                    tabIndex = event.index
                )

                getOrderBasedOnTheirTab()
            }

            OrderEvent.OnDismissErrorDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                )
            }

            OrderEvent.OnConfirmChangeStatus -> {
                _state.value = _state.value.copy(
                    isConfirmDialogOpen = false,
                    isConfirmCanceledDialogOpen = false,
                )
                if (
                    _state.value.statusTarget.statusNumber <= 3 ||
                    _state.value.statusTarget.statusNumber > 1
                ) {
                    changeOrderStatus()
                }
                getOrderBasedOnTheirTab()

                _state.value = _state.value.copy(
                    orderId = -69
                )
            }

            is OrderEvent.OnOpenChangeStatusDialog -> {
                if (event.statusTarget == StatusOrder.Canceled) {
                    _state.value = _state.value.copy(
                        orderId = event.id,
                        statusTarget = event.statusTarget,
                        isConfirmCanceledDialogOpen = true
                    )
                    return
                }
                _state.value = _state.value.copy(
                    orderId = event.id,
                    statusTarget = event.statusTarget,
                    isConfirmDialogOpen = true
                )
            }

            OrderEvent.OnDismissChangeStatusDialog -> {
                _state.value = _state.value.copy(
                    orderId = -69,
                    statusTarget = StatusOrder.Unidentified,
                    isConfirmDialogOpen = false,
                    isConfirmCanceledDialogOpen = false
                )
            }

            OrderEvent.OnRefreshPage -> {
                getTokenValue()
                getOrderBasedOnTheirTab()
            }

            OrderEvent.ClearToken -> {
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

    private fun getOrderBasedOnTheirTab() {
        when (_state.value.tabIndex) {
            // Unconfirmed
            0 -> {
                getOrder(1)
            }
            // Confirmed
            1 -> {
                getOrder(2)
            }
            // Finish
            2 -> {
                getOrder(3)
            }
            //Canceled
            3 -> {
                getOrder(0)
            }
        }
    }

    private fun getOrder(status: Int) {
        orderUseCases.getOrdersUseCase(_state.value.token, status).onEach { result ->
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
                        orders = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeOrderStatus() {
        orderUseCases.changeOrderStatusUseCase(
            id = _state.value.orderId,
            token = _state.value.token,
            status = if (_state.value.statusTarget == StatusOrder.Canceled) {
                0
            } else {
                _state.value.statusTarget.statusNumber
            }
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
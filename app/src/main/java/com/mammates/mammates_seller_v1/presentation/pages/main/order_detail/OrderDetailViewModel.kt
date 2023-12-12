package com.mammates.mammates_seller_v1.presentation.pages.main.order_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.order.OrderUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.util.StatusOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val orderUseCases: OrderUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(OrderDetailState())
    val state = _state.asStateFlow()

    init {

        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )

        savedStateHandle.get<Int>("order_id")?.let { id ->
            _state.value = _state.value.copy(
                id = id
            )
            if (id != -69) {
                orderUseCases.getOrderDetailUseCase(_state.value.token, id).onEach { result ->
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
                            result.data?.let {
                                _state.value = _state.value.copy(
                                    buyer = result.data.buyer ?: "",
                                    invoice = result.data.invoice ?: "",
                                    date = result.data.time ?: "",
                                    foods = result.data.foods,
                                    total = result.data.total ?: 0,
                                    status = when (result.data.status) {
                                        1 -> StatusOrder.Unconfirmed
                                        2 -> StatusOrder.Confirmed
                                        3 -> StatusOrder.Finish
                                        0 -> StatusOrder.Canceled
                                        else -> StatusOrder.Unidentified
                                    },
                                    isLoading = false
                                )
                            }

                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onEvent(event: OrderDetailEvent) {
        when (event) {
            OrderDetailEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                    isConfirmDialogOpen = false,
                )
            }

            is OrderDetailEvent.OnChangeStatusOrder -> {
                _state.value = _state.value.copy(
                    isConfirmDialogOpen = false,
                    isConfirmCanceledDialogOpen = false,
                )
                if (
                    _state.value.status.statusNumber <= 3 ||
                    _state.value.status.statusNumber > 1
                ) {
                    orderUseCases.changeOrderStatusUseCase(
                        id = _state.value.id,
                        token = _state.value.token,
                        status = if (event.isCanceled) {
                            0
                        } else {
                            _state.value.status.statusNumber + 1
                        }
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

            is OrderDetailEvent.OnOpenConfirmDialog -> {
                if (event.isCanceled) {
                    _state.value = _state.value.copy(
                        isConfirmCanceledDialogOpen = true
                    )
                    return
                }
                _state.value = _state.value.copy(
                    isConfirmDialogOpen = true
                )

            }
        }
    }
}
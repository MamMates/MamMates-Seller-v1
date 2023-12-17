package com.mammates.mammates_seller_v1.presentation.pages.main.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.account.AccountUseCases
import com.mammates.mammates_seller_v1.domain.use_case.food.FoodUseCases
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
class StoreViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val foodUseCases: FoodUseCases,
    private val accountUseCases: AccountUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(StoreState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: StoreEvent) {
        when (event) {
            StoreEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null
                )
            }

            StoreEvent.OnRefreshPage -> {
                getTokenValue()
                getStoreData()
                getFoodData()
            }

            StoreEvent.ClearToken -> {
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

    private fun getStoreData() {
        accountUseCases.getStoreUseCase(_state.value.token).onEach { result ->
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
                        storeName = result.data?.name ?: "No Data Store Name",
                        storeAddress = result.data?.address ?: "No Data Store Address",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFoodData() {
        foodUseCases.getAllFoodsUseCase(_state.value.token).onEach { result ->
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
                        foods = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
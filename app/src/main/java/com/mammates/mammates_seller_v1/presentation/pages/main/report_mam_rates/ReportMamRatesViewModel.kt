package com.mammates.mammates_seller_v1.presentation.pages.main.report_mam_rates

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.MamRatesUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class ReportMamRatesViewModel @Inject constructor(
    private val mamRatesUseCases: MamRatesUseCases,
    private val tokenUseCases: TokenUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ReportMamRatesState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: ReportMamRatesEvent) {
        when (event) {
            is ReportMamRatesEvent.OnChangeFoodImageUri -> {
                _state.value = _state.value.copy(
                    foodImage = event.uri,
                )
            }

            is ReportMamRatesEvent.OnChangeFoodName -> {
                _state.value = _state.value.copy(
                    foodName = event.foodName,
                    foodNameValidation = emptyValidation(event.foodName, "Food Name")
                )
            }

            is ReportMamRatesEvent.OnChangeFoodPrice -> {
                try {
                    _state.value = _state.value.copy(
                        foodPrice = event.foodPrice.toInt(),
                        foodPriceValidation = emptyValidation(event.foodPrice, "Price")
                    )
                } catch (e: NumberFormatException) {
                    _state.value = _state.value.copy(
                        foodPriceValidation = "Price can't input other than number",
                        foodPrice = 0
                    )
                }
            }

            is ReportMamRatesEvent.OnChangeRating -> {

                try {
                    _state.value = _state.value.copy(
                        rating = event.rating.toInt(),
                        ratingValidation = if (event.rating.toInt() > 3) {
                            "Rating cannot above 3"
                        } else if (event.rating.toInt() < 0) {
                            "Rating cannot below 0"
                        } else {
                            emptyValidation(event.rating, "Price")
                        }
                    )
                } catch (e: NumberFormatException) {
                    _state.value = _state.value.copy(
                        ratingValidation = "Price can't input other than number",
                        rating = 0
                    )
                }

            }

            ReportMamRatesEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                )
            }

            ReportMamRatesEvent.ClearToken -> {
                viewModelScope.launch {
                    tokenUseCases.clearTokenUseCase()
                }
                _state.value = _state.value.copy(
                    token = ""
                )
            }


            is ReportMamRatesEvent.OnSubmitReport -> {
                submitReport(event.context)
            }
        }
    }

    private fun getTokenValue() {
        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )
    }

    private fun submitReport(context: Context) {

        validateAllFieldValue()

        if (
            !_state.value.foodNameValidation.isNullOrEmpty() &&
            !_state.value.ratingValidation.isNullOrEmpty() &&
            !_state.value.foodImageValidation.isNullOrEmpty() &&
            !_state.value.foodPriceValidation.isNullOrEmpty()
        ) {
            return
        }

        val imgFile = ImageUtils.uriToFile(_state.value.foodImage, context)

        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imgFile.name,
            imgFile.asRequestBody("image/jpeg".toMediaType())
        )

        mamRatesUseCases.reportMamRatesUseCase(
            image = multipartBody,
            name = _state.value.foodName,
            price = _state.value.foodPrice,
            rating = _state.value.rating,
            token = _state.value.token
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
                        successMessage = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateAllFieldValue() {
        _state.value = _state.value.copy(
            foodImageValidation = if (
                _state.value.foodImage == Uri.EMPTY
            ) {
                "Please input your food image !!"
            } else {
                null
            },
            foodNameValidation = emptyValidation(_state.value.foodName, "Food Name"),
            foodPriceValidation = try {
                emptyValidation(
                    if (_state.value.foodPrice == 0) {
                        ""
                    } else {
                        _state.value.foodPrice.toString()
                    }, "Price"
                )
            } catch (e: NumberFormatException) {
                "Price can't input other than number"
            },
            ratingValidation = try {
                if (_state.value.rating > 3) {
                    "Rating cannot above 3"
                } else if (_state.value.rating < 0) {
                    "Rating cannot below 0"
                } else {
                    emptyValidation(
                        if (_state.value.rating == 0) {
                            ""
                        } else {
                            _state.value.rating.toString()
                        }, "Price"
                    )
                }
            } catch (e: NumberFormatException) {
                "Price can't input other than number"
            }
        )
    }


}
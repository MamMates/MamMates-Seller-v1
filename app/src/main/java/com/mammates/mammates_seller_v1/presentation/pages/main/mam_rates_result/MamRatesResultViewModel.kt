package com.mammates.mammates_seller_v1.presentation.pages.main.mam_rates_result

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.MamRatesUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.util.Category
import com.mammates.mammates_seller_v1.util.HttpError
import com.mammates.mammates_seller_v1.util.ImageUtils
import com.mammates.mammates_seller_v1.util.ImageUtils.Companion.reduceFileImage
import com.mammates.mammates_seller_v1.util.Rating
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
class MamRatesResultViewModel @Inject constructor(
    private val tokenUseCases: TokenUseCases,
    private val mamRatesUseCases: MamRatesUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MamRatesResultState())
    val state = _state.asStateFlow()

    init {
        getTokenValue()
    }

    fun onEvent(event: MamRatesResultEvent) {
        when (event) {
            is MamRatesResultEvent.OnCaptureImage -> {
                _state.value = _state.value.copy(
                    imageUri = event.uri
                )
            }

            MamRatesResultEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null
                )
            }

            is MamRatesResultEvent.OnGetMamRates -> {
                if (_state.value.imageUri == Uri.EMPTY) {
                    _state.value = _state.value.copy(
                        errorMessage = "Please Input Your Photo"
                    )
                    return
                }

                getMamRates(event.context)

            }

            MamRatesResultEvent.OnDismissNotAuthorize -> {
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

    private fun getMamRates(context: Context) {
        val imgFile = ImageUtils.uriToFile(_state.value.imageUri, context).reduceFileImage()

        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imgFile.name,
            imgFile.asRequestBody("image/jpeg".toMediaType())
        )

        mamRatesUseCases.getMamRatesUseCase(
            token = _state.value.token,
            image = multipartBody
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
                    result.data?.let { data ->
                        _state.value = _state.value.copy(
                            successMessage = result.message,
                            rating = when (data.rating) {
                                1 -> Rating.ONE
                                2 -> Rating.TWO
                                3 -> Rating.THREE
                                0 -> Rating.ZERO
                                else -> Rating.Undefine
                            },
                            category = when (data.category) {
                                0 -> Category.Bika_Ambon.name.replace("_", " ")
                                1 -> Category.Dadar_Gulung.name.replace("_", " ")
                                2 -> Category.Donat.name.replace("_", " ")
                                3 -> Category.Kue_Cubit.name.replace("_", " ")
                                4 -> Category.Kue_Klepon.name.replace("_", " ")
                                5 -> Category.Kue_Lapis.name.replace("_", " ")
                                6 -> Category.Kue_Lumpur.name.replace("_", " ")
                                7 -> Category.Kue_Risoles.name.replace("_", " ")
                                8 -> Category.Putu_Ayu.name.replace("_", " ")
                                9 -> Category.Roti.name.replace("_", " ")
                                else -> Category.Undefine.name.replace("_", " ")

                            },
                            price = data.price ?: -69,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}
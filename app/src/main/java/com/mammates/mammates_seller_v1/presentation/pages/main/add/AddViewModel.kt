package com.mammates.mammates_seller_v1.presentation.pages.main.add

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.food.FoodUseCases
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.MamRatesUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
import com.mammates.mammates_seller_v1.util.Category
import com.mammates.mammates_seller_v1.util.ImageUtils
import com.mammates.mammates_seller_v1.util.Rating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val foodUseCases: FoodUseCases,
    private val mamRatesUseCases: MamRatesUseCases,
    private val tokenUseCases: TokenUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddState())
    val state = _state.asStateFlow()

    init {

        getTokenValue()

        savedStateHandle.get<Int>("food_id")?.let { id ->
            _state.value = _state.value.copy(
                id = id
            )
            if (id != -69) {
                if (_state.value.token.isNotEmpty()) {
                    getFoodDetailData()
                }
            }
        }
    }

    fun onEvent(event: AddEvent) {
        when (event) {
            is AddEvent.OnChangeFoodDisplayImage -> {
                _state.value = _state.value.copy(
                    foodDisplayImage = event.uri
                )
            }

            is AddEvent.OnChangeFoodMamRatesImage -> {
                _state.value = _state.value.copy(
                    foodMamRatesImage = event.uri,
                    foodMamRatesImageValidation = null
                )
            }

            is AddEvent.OnChangeFoodName -> {
                _state.value = _state.value.copy(
                    foodName = event.name,
                    foodNameValidation = emptyValidation(event.name, "Food Name")
                )
            }

            is AddEvent.OnChangeFoodPrice -> {
                try {
                    _state.value = _state.value.copy(
                        foodPrice = event.price.toInt()
                    )
                } catch (e: NumberFormatException) {
                    _state.value = _state.value.copy(
                        foodPriceValidation = "Price can't input other than number"
                    )
                }
                _state.value = _state.value.copy(
                    foodPriceValidation = emptyValidation(event.price, "Price")
                )
            }

            is AddEvent.OnGenerateMamRatesFromImage -> {
                if (_state.value.foodMamRatesImage == Uri.EMPTY) {
                    _state.value = _state.value.copy(
                        foodMamRatesImageValidation = "Please input your food image !!"
                    )
                    return
                }

                getMamRates(event.context)

            }

            is AddEvent.OnUpdateAddFood -> {
                validateAllFieldValue()

                if (
                    !_state.value.foodNameValidation.isNullOrEmpty() &&
                    !_state.value.foodPriceValidation.isNullOrEmpty() &&
                    !_state.value.foodMamRatesImageValidation.isNullOrEmpty()
                ) {
                    return
                }
                if (_state.value.isEdit) {
                    editFood(event.context)
                    return
                }
                addFood(event.context)
            }
        }
    }

    private fun getTokenValue() {
        _state.value = _state.value.copy(
            token = tokenUseCases.getTokenUseCase()
        )
    }

    private fun editFood(context: Context) {
        foodUseCases.updateFoodUseCase(
            id = _state.value.id,
            token = _state.value.token,
            image = imageMultiPartData(
                context,
                _state.value.foodDisplayImage,
                _state.value.foodDisplayUrlImage
            ),
            name = _state.value.foodName,
            price = _state.value.foodPrice,
            category = _state.value.foodCategory ?: Category.Undefine.name,
            mamImage = imageMultiPartData(
                context,
                _state.value.foodMamRatesImage,
                _state.value.foodMamRatesUrlImage
            ),
            mamRates = _state.value.rating.rating,
        ).onEach {
            TODO("IMPLEMENT THE DIALOG")
        }
    }

    private fun addFood(context: Context) {
        foodUseCases.addFoodUseCase(
            token = _state.value.token,
            image = imageMultiPartData(
                context,
                _state.value.foodDisplayImage,
                _state.value.foodDisplayUrlImage
            ),
            name = _state.value.foodName,
            price = _state.value.foodPrice,
            category = _state.value.foodCategory ?: Category.Undefine.name,
            mamImage = imageMultiPartData(
                context,
                _state.value.foodMamRatesImage,
                _state.value.foodMamRatesUrlImage
            ),
            mamRates = _state.value.rating.rating,
        ).onEach {
            TODO("IMPLEMENT THE DIALOG")
        }
    }

    private fun imageMultiPartData(context: Context, uri: Uri, url: String?): MultipartBody.Part {
        if (
            uri == Uri.EMPTY &&
            url.isNullOrEmpty()
        ) {
            return MultipartBody.Part.createFormData(
                name = "image",
                value = "nill",
            )
        } else if (!url.isNullOrEmpty()) {
            return MultipartBody.Part.createFormData(
                name = "image",
                value = url,
            )
        } else {
            val imgFile =
                ImageUtils.uriToFile(uri, context)

            return MultipartBody.Part.createFormData(
                name = "image",
                filename = imgFile.name,
                body = imgFile.asRequestBody("*/*".toMediaType())


            )
        }
    }

    private fun validateAllFieldValue() {
        _state.value = _state.value.copy(
            foodNameValidation = emptyValidation(_state.value.foodName, "Food Name"),
            foodMamRatesImageValidation = if (
                _state.value.foodMamRatesImage == Uri.EMPTY &&
                _state.value.foodMamRatesUrlImage.isNullOrEmpty()
            ) {
                "Please input your food image !!"
            } else {
                null
            },
            foodPriceValidation = emptyValidation(_state.value.foodPrice.toString(), "Price"),
        )
    }

    private fun getFoodDetailData() {
        foodUseCases.getFoodDetailUseCase(_state.value.token, _state.value.id).onEach { result ->
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
                    result.data?.let { data ->
                        _state.value = _state.value.copy(
                            foodDisplayUrlImage = data.image,
                            foodName = data.name ?: "",
                            foodPriceSuggestion = data.price,
                            foodMamRatesUrlImage = data.mamImage,
                            rating = when (data.mamRates) {
                                0 -> Rating.ZERO
                                1 -> Rating.ONE
                                2 -> Rating.TWO
                                3 -> Rating.THREE
                                else -> Rating.Undefine
                            },
                            foodCategory = when (data.category) {
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
                            }
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMamRates(context: Context) {
        val imgFile = ImageUtils.uriToFile(_state.value.foodMamRatesImage, context)

        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imgFile.name,
            imgFile.asRequestBody("*/*".toMediaType())
        )

        mamRatesUseCases.getMamRatesUseCase(
            token = _state.value.token,
            image = multipartBody
        ).onEach { result ->
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
                            foodCategory = when (data.category) {
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
                            foodPrice = data.price ?: -69,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}
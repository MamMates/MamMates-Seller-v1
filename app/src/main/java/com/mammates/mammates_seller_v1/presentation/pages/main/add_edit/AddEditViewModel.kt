package com.mammates.mammates_seller_v1.presentation.pages.main.add_edit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.use_case.food.FoodUseCases
import com.mammates.mammates_seller_v1.domain.use_case.mam_rates.MamRatesUseCases
import com.mammates.mammates_seller_v1.domain.use_case.token.TokenUseCases
import com.mammates.mammates_seller_v1.presentation.util.validation.emptyValidation
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
class AddEditViewModel @Inject constructor(
    private val foodUseCases: FoodUseCases,
    private val mamRatesUseCases: MamRatesUseCases,
    private val tokenUseCases: TokenUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditState())
    val state = _state.asStateFlow()

    init {

        getTokenValue()

        savedStateHandle.get<Int>("food_id")?.let { id ->
            _state.value = _state.value.copy(
                id = id,

                )
            if (id != -69) {
                _state.value = _state.value.copy(
                    isEdit = true
                )
                if (_state.value.token.isNotEmpty()) {
                    getFoodDetailData()
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnChangeFoodDisplayImage -> {
                _state.value = _state.value.copy(
                    foodDisplayImage = event.uri
                )
            }

            is AddEditEvent.OnChangeFoodMamRatesImage -> {
                _state.value = _state.value.copy(
                    foodMamRatesImage = event.uri,
                    foodMamRatesImageValidation = null
                )
            }

            is AddEditEvent.OnChangeFoodName -> {
                _state.value = _state.value.copy(
                    foodName = event.name,
                    foodNameValidation = emptyValidation(event.name, "Food Name")
                )
            }

            is AddEditEvent.OnChangeFoodPrice -> {
                try {
                    _state.value = _state.value.copy(
                        foodPrice = event.price.toInt(),
                        foodPriceValidation = emptyValidation(event.price, "Price")
                    )
                } catch (e: NumberFormatException) {
                    _state.value = _state.value.copy(
                        foodPriceValidation = "Price can't input other than number",
                        foodPrice = -69,
                    )
                }
            }

            is AddEditEvent.OnGenerateMamRatesFromImage -> {
                if (_state.value.foodMamRatesImage == Uri.EMPTY) {
                    _state.value = _state.value.copy(
                        foodMamRatesImageValidation = "Please input your food image !!"
                    )
                    return
                }

                getMamRates(event.context)

            }

            is AddEditEvent.OnUpdateAddFood -> {
                validateAllFieldValue()

                if (_state.value.rating == Rating.Undefine ||
                    _state.value.foodCategory == Category.Undefine.value ||
                    _state.value.foodPriceSuggestion == -69
                ) {
                    _state.value = _state.value.copy(
                        errorMessage = "Please generate your MamRates !"
                    )
                    return
                }

                if (
                    !_state.value.foodNameValidation.isNullOrEmpty() ||
                    !_state.value.foodPriceValidation.isNullOrEmpty() ||
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

            AddEditEvent.OnDismissDialog -> {
                _state.value = _state.value.copy(
                    errorMessage = null,
                    successMessage = null,
                    isConfirmDeleteDialogOpen = false
                )
            }

            AddEditEvent.OnDeleteFood -> {
                deleteFood()
            }

            AddEditEvent.OnOpenDeleteDialog -> {
                _state.value = _state.value.copy(
                    isConfirmDeleteDialogOpen = true
                )
            }

            AddEditEvent.ClearToken -> {
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

    private fun deleteFood() {
        foodUseCases.deleteFoodUseCase(_state.value.token, _state.value.id)
            .onEach { result ->
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
                            isConfirmDeleteDialogOpen = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            isConfirmDeleteDialogOpen = false
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            successMessage = result.data,
                            isLoading = false,
                            isConfirmDeleteDialogOpen = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun editFood(context: Context) {

        if (_state.value.foodMamRatesImage != Uri.EMPTY) {
            _state.value = _state.value.copy(
                foodMamRatesUrlImage = null
            )
        }

        foodUseCases.updateFoodUseCase(
            id = _state.value.id,
            token = _state.value.token,
            image = imageMultiPartData(
                context,
                "image",
                _state.value.foodDisplayImage,
                _state.value.foodDisplayUrlImage
            ),
            name = _state.value.foodName,
            price = _state.value.foodPrice,
            category = _state.value.foodCategory ?: Category.Undefine.value,
            mamImage = imageMultiPartData(
                context,
                "mam_image",
                _state.value.foodMamRatesImage,
                _state.value.foodMamRatesUrlImage
            ),
            mamRates = _state.value.rating.rating,
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
                        isLoading = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addFood(context: Context) {
        foodUseCases.addFoodUseCase(
            token = _state.value.token,
            image = imageMultiPartData(
                context,
                "image",
                _state.value.foodDisplayImage,
                _state.value.foodDisplayUrlImage
            ),
            name = _state.value.foodName,
            price = _state.value.foodPrice,
            category = _state.value.foodCategory ?: Category.Undefine.value,
            mamImage = imageMultiPartData(
                context,
                "mam_image",
                _state.value.foodMamRatesImage,
                _state.value.foodMamRatesUrlImage
            ),
            mamRates = _state.value.rating.rating,
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
                        isLoading = false,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun imageMultiPartData(
        context: Context,
        name: String,
        uri: Uri,
        url: String?
    ): MultipartBody.Part {
        if (
            uri == Uri.EMPTY &&
            url.isNullOrEmpty()
        ) {
            Log.i("AddEditViewModel", "Here Kosong")
            return MultipartBody.Part.createFormData(
                name = name,
                value = "nil",
            )
        } else if (!url.isNullOrEmpty()) {
            Log.i("AddEditViewModel", "Here url dah ada")
            return MultipartBody.Part.createFormData(
                name = name,
                value = url,
            )
        } else {
            val imgFile =
                ImageUtils.uriToFile(uri, context).reduceFileImage()
            Log.i("AddEditViewModel", "Here buat file")
            return MultipartBody.Part.createFormData(
                name = name,
                filename = imgFile.name,
                body = imgFile.asRequestBody("image/jpeg".toMediaType())


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
                "Please input your food image!!"
            } else if (_state.value.foodMamRatesImage == Uri.EMPTY) {
                "Please update your mamRates!!"
            } else {
                null
            },
            foodPriceValidation = emptyValidation(
                if (_state.value.foodPrice == -69) {
                    ""
                } else {
                    _state.value.foodPrice.toString()
                }, "Price"
            ),
        )
    }

    private fun getFoodDetailData() {
        foodUseCases.getFoodDetailUseCase(_state.value.token, _state.value.id).onEach { result ->
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
                            foodDisplayUrlImage = data.image,
                            foodName = data.name ?: "",
                            foodPrice = data.price ?: -69,
                            foodMamRatesUrlImage = data.mamImage,
                            rating = when (data.mamRates) {
                                0 -> Rating.ZERO
                                1 -> Rating.ONE
                                2 -> Rating.TWO
                                3 -> Rating.THREE
                                else -> Rating.Undefine
                            },
                            foodCategory = when (data.category) {
                                0 -> Category.Bika_Ambon.value
                                1 -> Category.Dadar_Gulung.value
                                2 -> Category.Donat.value
                                3 -> Category.Kue_Cubit.value
                                4 -> Category.Kue_Klepon.value
                                5 -> Category.Kue_Lapis.value
                                6 -> Category.Kue_Lumpur.value
                                7 -> Category.Kue_Risoles.value
                                8 -> Category.Putu_Ayu.value
                                9 -> Category.Roti.value
                                else -> Category.Undefine.value
                            },
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMamRates(context: Context) {
        val imgFile = ImageUtils.uriToFile(_state.value.foodMamRatesImage, context).reduceFileImage()

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
                            foodCategory = when (data.category) {
                                0 -> Category.Bika_Ambon.value
                                1 -> Category.Dadar_Gulung.value
                                2 -> Category.Donat.value
                                3 -> Category.Kue_Cubit.value
                                4 -> Category.Kue_Klepon.value
                                5 -> Category.Kue_Lapis.value
                                6 -> Category.Kue_Lumpur.value
                                7 -> Category.Kue_Risoles.value
                                8 -> Category.Putu_Ayu.value
                                9 -> Category.Roti.value
                                else -> Category.Undefine.value
                            },
                            foodPriceSuggestion = data.price ?: -69,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}
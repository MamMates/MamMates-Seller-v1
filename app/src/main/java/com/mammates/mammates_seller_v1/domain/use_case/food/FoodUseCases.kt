package com.mammates.mammates_seller_v1.domain.use_case.food

data class FoodUseCases(
    val addFoodUseCase: AddFoodUseCase,
    val deleteFoodUseCase: DeleteFoodUseCase,
    val getAllFoodsUseCase: GetAllFoodsUseCase,
    val getFoodDetailUseCase: GetFoodDetailUseCase,
    val updateFoodUseCase: UpdateFoodUseCase,
)
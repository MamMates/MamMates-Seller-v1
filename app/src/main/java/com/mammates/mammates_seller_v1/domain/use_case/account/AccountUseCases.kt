package com.mammates.mammates_seller_v1.domain.use_case.account

data class AccountUseCases(
    val getAccountUseCase: GetAccountUseCase,
    val getStoreUseCase: GetStoreUseCase,
    val updateAccountUseCase: UpdateAccountUseCase,
    val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
)
package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.AccountSeller
import com.mammates.mammates_seller_v1.domain.model.Store
import okhttp3.MultipartBody

interface AccountRepository {
    suspend fun getStore(
        token: String,
    ): ResMamMates<Store>

    suspend fun getAccount(
        token: String,
    ): ResMamMates<AccountSeller>

    suspend fun updateAccount(
        token: String,
        seller: String,
        address: String,
        store: String,
        email: String
    ): ResMamMates<String>

    suspend fun updateProfilePicture(
        token: String,
        image: MultipartBody.Part,
    ): ResMamMates<String>

    suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String,
    ): ResMamMates<String>

}
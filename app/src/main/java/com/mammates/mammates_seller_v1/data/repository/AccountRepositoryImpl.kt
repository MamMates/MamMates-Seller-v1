package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqAccountSeller
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqPasswordChange
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.AccountSeller
import com.mammates.mammates_seller_v1.domain.model.Store
import com.mammates.mammates_seller_v1.domain.repository.AccountRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: MamMatesApi
) : AccountRepository {

    override suspend fun getStore(token: String): ResMamMates<Store> {
        return api.getStore(token)
    }

    override suspend fun getAccount(token: String): ResMamMates<AccountSeller> {
        return api.getAccount(token)
    }

    override suspend fun updateAccount(
        token: String,
        seller: String,
        address: String,
        store: String,
        email: String
    ): ResMamMates<String> {
        return api.updateAccount(
            token = token,
            reqAccountSeller = ReqAccountSeller(
                seller, address, store, email
            )
        )
    }

    override suspend fun updateProfilePicture(
        token: String,
        image: MultipartBody.Part
    ): ResMamMates<String> {
        return api.updateProfilePicture(
            token, image
        )
    }

    override suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        newPasswordConfirm: String
    ): ResMamMates<String> {
        return api.changePassword(
            token = token,
            reqPasswordChange = ReqPasswordChange(
                oldPassword = oldPassword,
                newPassword = newPassword,
                newPasswordRepeat = newPasswordConfirm
            )
        )
    }
}
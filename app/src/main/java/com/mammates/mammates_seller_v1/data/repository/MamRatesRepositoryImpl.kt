package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.MamRates
import com.mammates.mammates_seller_v1.domain.repository.MamRatesRepository
import com.mammates.mammates_seller_v1.util.createPartFromString
import okhttp3.MultipartBody
import javax.inject.Inject

class MamRatesRepositoryImpl @Inject constructor(
    private val api: MamMatesApi
) : MamRatesRepository {
    override suspend fun getMamRates(
        token: String,
        image: MultipartBody.Part
    ): ResMamMates<MamRates> {
        return api.getMamRates(token, image)
    }

    override suspend fun reportMamRates(
        token: String,
        name: String,
        price: Int,
        rating: Int,
        image: MultipartBody.Part
    ): ResMamMates<String> {
        return api.reportMamRates(
            token, createPartFromString(name), price, rating, image
        )
    }
}
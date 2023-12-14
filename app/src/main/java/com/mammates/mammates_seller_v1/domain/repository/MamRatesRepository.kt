package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.MamRates
import okhttp3.MultipartBody

interface MamRatesRepository {
    suspend fun getMamRates(
        token: String,
        image: MultipartBody.Part
    ): ResMamMates<MamRates>

    suspend fun reportMamRates(
        token: String,
        name: String,
        price: Int,
        rating: Int,
        image: MultipartBody.Part,
    ): ResMamMates<String>
}
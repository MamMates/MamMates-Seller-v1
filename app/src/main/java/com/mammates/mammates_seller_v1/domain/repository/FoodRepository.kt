package com.mammates.mammates_seller_v1.domain.repository

import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.FoodDetail
import com.mammates.mammates_seller_v1.domain.model.Foods
import okhttp3.MultipartBody

interface FoodRepository {
    suspend fun getAllFoods(
        token: String,
    ): ResMamMates<Foods>

    suspend fun addFood(
        token: String,
        image: MultipartBody.Part,
        name: String,
        price: Int,
        category: String,
        mamImage: MultipartBody.Part,
        mamRates: Int,
    ): ResMamMates<String>

    suspend fun getFoodDetail(
        token: String,
        id: Int
    ): ResMamMates<FoodDetail>

    suspend fun updateFood(
        token: String,
        id: Int,
        image: MultipartBody.Part,
        name: String,
        price: Int,
        category: String,
        mamImage: MultipartBody.Part,
        mamRates: Int,
    ): ResMamMates<String>

    suspend fun deleteFood(
        token: String,
        id: Int,
    ): ResMamMates<String>
}
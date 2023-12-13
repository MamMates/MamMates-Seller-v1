package com.mammates.mammates_seller_v1.data.repository

import com.mammates.mammates_seller_v1.data.source.remote.MamMatesApi
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.FoodDetail
import com.mammates.mammates_seller_v1.domain.model.Foods
import com.mammates.mammates_seller_v1.domain.repository.FoodRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    val api: MamMatesApi
) : FoodRepository {
    override suspend fun getAllFoods(token: String): ResMamMates<Foods> {
        return api.getAllFoods(token)
    }

    override suspend fun addFood(
        token: String,
        image: MultipartBody.Part?,
        name: String,
        price: Int,
        category: String,
        mamImage: MultipartBody.Part,
        mamRates: Int
    ): ResMamMates<String> {
        return api.addFood(
            token, image, name, price, category, mamImage, mamRates
        )
    }

    override suspend fun getFoodDetail(token: String, id: Int): ResMamMates<FoodDetail> {
        return api.getFoodDetail(token, id)
    }

    override suspend fun updateFood(
        token: String,
        id: Int,
        image: MultipartBody.Part?,
        name: String,
        price: Int,
        category: String,
        mamImage: MultipartBody.Part,
        mamRates: Int
    ): ResMamMates<String> {
        return api.updateFood(token, id, image, name, price, category, mamImage, mamRates)
    }

    override suspend fun deleteFood(token: String, id: Int): ResMamMates<String> {
        return api.deleteFood(token, id)
    }
}
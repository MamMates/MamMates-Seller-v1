package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class FoodDetail(

    @field:SerializedName("food")
    val food: Food? = null
)

data class Food(

    @field:SerializedName("mam_rates")
    val mamRates: Int? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("mam_image")
    val mamImage: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("category")
    val category: Int? = null
)

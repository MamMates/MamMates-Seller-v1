package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class FoodOrderItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
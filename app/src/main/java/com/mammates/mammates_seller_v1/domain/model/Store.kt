package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class Store(

    @field:SerializedName("store")
    val store: StoreItem? = null,
)

data class StoreItem(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)


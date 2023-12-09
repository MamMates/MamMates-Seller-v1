package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName


data class OrdersRecent(

    @field:SerializedName("orders")
    val orders: List<OrderRecentItems?>? = null
)

data class OrderRecentItems(

    @field:SerializedName("buyer")
    val buyer: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName


data class Orders(

    @field:SerializedName("orders")
    val orders: List<OrdersItem?>? = null
)

data class OrdersItem(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("foods")
    val foods: List<FoodOrderItem?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("buyer")
    val buyer: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)


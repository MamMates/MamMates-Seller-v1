package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName


data class OrderDetail(

    @field:SerializedName("order")
    val order: OrderDetailItem? = null
)

data class OrderDetailItem(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("foods")
    val foods: List<FoodOrderItem?>? = null,

    @field:SerializedName("category")
    val category: Int? = null,

    @field:SerializedName("invoice")
    val invoice: String? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("buyer")
    val buyer: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)





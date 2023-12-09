package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class Foods(

    @field:SerializedName("foods")
    val foods: List<FoodsItem?>? = null
)

data class FoodsItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("is_valid")
    val isValid: Boolean? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("stock")
    val stock: Int? = null,

    @field:SerializedName("mam_rates")
    val mamRates: Int? = null
)

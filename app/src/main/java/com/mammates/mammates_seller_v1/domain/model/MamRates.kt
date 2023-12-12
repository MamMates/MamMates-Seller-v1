package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class MamRates(
    @field:SerializedName("mam_mates")
    val mamMates: MamRatesItem? = null,
)

data class MamRatesItem(
    @field:SerializedName("category")
    val category: Int? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,
)

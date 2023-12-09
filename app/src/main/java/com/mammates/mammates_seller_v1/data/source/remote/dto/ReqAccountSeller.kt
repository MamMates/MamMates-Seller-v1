package com.mammates.mammates_seller_v1.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqAccountSeller(

    @field:SerializedName("seller")
    val seller: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("store")
    val store: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

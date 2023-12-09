package com.mammates.mammates_seller_v1.domain.model

import com.google.gson.annotations.SerializedName

data class AccountSeller(

    @field:SerializedName("account")
    val account: AccountItems? = null,

    )

data class AccountItems(
    @field:SerializedName("store")
    val store: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("seller")
    val seller: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("image")
    val image: String? = null
)

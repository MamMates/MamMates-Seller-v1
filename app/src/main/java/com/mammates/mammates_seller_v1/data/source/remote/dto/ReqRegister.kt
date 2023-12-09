package com.mammates.mammates_seller_v1.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqRegister(

    @field:SerializedName("seller")
    val seller: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("store")
    val store: String,

    @field:SerializedName("password_repeat")
    val passwordRepeat: String,

    @field:SerializedName("email")
    val email: String
)

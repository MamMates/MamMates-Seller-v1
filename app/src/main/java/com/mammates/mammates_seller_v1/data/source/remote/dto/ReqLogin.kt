package com.mammates.mammates_seller_v1.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqLogin(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,
)
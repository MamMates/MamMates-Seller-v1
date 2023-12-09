package com.mammates.mammates_seller_v1.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqStatusChange(
    @field:SerializedName("status")
    val status: Int
)
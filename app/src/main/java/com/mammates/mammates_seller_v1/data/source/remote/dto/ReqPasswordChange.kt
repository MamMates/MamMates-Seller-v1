package com.mammates.mammates_seller_v1.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqPasswordChange(

    @field:SerializedName("old_password")
    val oldPassword: String? = null,

    @field:SerializedName("new_password")
    val newPassword: String? = null,

    @field:SerializedName("new_password_repeat")
    val newPasswordRepeat: String? = null
)

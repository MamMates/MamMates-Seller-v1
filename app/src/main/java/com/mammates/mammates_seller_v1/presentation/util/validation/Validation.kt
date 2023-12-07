package com.mammates.mammates_seller_v1.presentation.util.validation

import android.util.Patterns

fun emptyValidation(value: String, label: String): String? {
    return if (value.isEmpty()) "$label can't empty" else null
}

fun emailValidation(email: String): String? {
    if (email.isEmpty()) return "Email can't empty"
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Email is invalid"
    return null
}

fun passwordValidation(password: String): String? {
    if (password.isEmpty()) return "Password can't empty"
    if (password.length < 8) return "Password can't length less than 8"
    return null
}
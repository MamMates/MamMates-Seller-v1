package com.mammates.mammates_seller_v1.util

enum class ErrorMessage(val message: String) {
    JSON_PARSE("Cannot get the data that provides!"),
    NO_INTERNET("Couldn't reach server. Check your internet connection."),
    UNEXPECTED("An unexpected error occurred")
}
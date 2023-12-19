package com.mammates.mammates_seller_v1.util

enum class ErrorMessage(val message: String) {
    JSON_PARSE("Cannot get the data that provides!"),
    NO_INTERNET("Couldn't reach server. Check your internet connection."),
    UNEXPECTED("An unexpected error occurred"),
    DIFFERENT_ROLES("You login with different roles, please check your app or your account!"),
    TOKEN_INVALID("Your token is invalid, please try again!")
}
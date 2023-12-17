package com.mammates.mammates_seller_v1.util

enum class HttpError(val code: Int, val message: String) {
    BAD_REQUEST(400, "Please fill your data correctly!"),
    UNAUTHORIZED(401, "Unauthorized user please login to continue!"),
    FORBIDDEN(403, "You can't access this please go back!"),
    NOT_FOUND(404, "Sorry, the data was not found!, please try again"),
    CONFLICT(409, "Your email has been used, please use another email!"),
    INTERNAL_SERVER_ERROR(500, "Something went wrong, this server encountered internal error!"),
}

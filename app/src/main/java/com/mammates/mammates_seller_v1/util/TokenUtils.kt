package com.mammates.mammates_seller_v1.util

import java.util.Base64

class TokenUtils {
    companion object {
        fun decodedToken(token: String): String {
            val parts = token.split(".")
            try {
                val charset = charset("UTF-8")
                return String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            } catch (e: IllegalArgumentException) {
                throw e
            }
        }
    }
}
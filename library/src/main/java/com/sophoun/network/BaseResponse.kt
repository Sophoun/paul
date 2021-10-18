package com.sophoun.network

/**
 * Base response class for network request.
 */
data class BaseResponse<T> (
    val meta: Info,
    val data: T?
) {
    data class Info(
        val status: Status,
        val message: String
    )

    enum class Status {
        SUCCESS, REDIRECTION, CLIENT_ERROR, SERVER_ERROR, UNKNOWN, CONNECTION_ERROR
    }
}
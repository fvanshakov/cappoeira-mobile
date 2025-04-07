package ru.cappoeira.app.network

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        val result = apiCall()
        NetworkResult.Success(result)
    } catch (e: Exception) {
        val message = when (e) {
            is io.ktor.client.plugins.ClientRequestException -> "Client error: ${e.response.status}"
            is io.ktor.client.plugins.ServerResponseException -> "Server error: ${e.response.status}"
            is io.ktor.client.plugins.HttpRequestTimeoutException -> "Request timeout"
            else -> "Unknown error"
        }
        NetworkResult.Error(message, e)
    }
}

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : NetworkResult<Nothing>()
}

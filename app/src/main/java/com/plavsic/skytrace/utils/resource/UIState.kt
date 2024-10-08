package com.plavsic.skytrace.utils.resource


sealed class UIState<out T> {
    data object Idle : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    sealed class Error : UIState<Nothing>() {
        data class NetworkError(val message: String) : Error()
        data class ServerError(val code: Int, val message: String) : Error()
        data class UnknownError(val exception: Throwable) : Error()
    }
}



package com.plavsic.skytrace.utils


sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<out T>(val data: List<T>) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}
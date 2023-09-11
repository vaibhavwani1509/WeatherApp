package com.vaibhavwani.weatherapp.data.api

/**
 * Sealed class to represent the State of the API call
 */
sealed class ApiState<out T> {
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}
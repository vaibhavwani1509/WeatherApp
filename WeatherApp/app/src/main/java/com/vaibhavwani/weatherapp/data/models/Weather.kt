package com.vaibhavwani.weatherapp.data.models

/**
 * Weather - Data class generated using the plugin - JSON to Kotlin
 */
data class Weather(
    val current: Current = Current(),
    val location: Location = Location(),
    val request: Request = Request()
)
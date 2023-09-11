package com.vaibhavwani.weatherapp.data.models

/**
 * Current - Data class generated using the plugin - JSON to Kotlin
 */
data class Current(
    val cloudcover: Number = 0,
    val feelslike: Number = 0,
    val humidity: Number = 0,
    val is_day: String = "",
    val observation_time: String = "",
    val precip: Number = 0,
    val pressure: Number = 0,
    val temperature: Number = 0,
    val uv_index: Number = 0,
    val visibility: Number = 0,
    val weather_code: Number = 0,
    val weather_descriptions: List<String> = listOf(),
    val weather_icons: List<String> = listOf(),
    val wind_degree: Number = 0,
    val wind_dir: String = "",
    val wind_speed: Number = 0
)
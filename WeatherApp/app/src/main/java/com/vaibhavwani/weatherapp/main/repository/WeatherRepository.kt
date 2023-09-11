package com.vaibhavwani.weatherapp.main.repository

import com.vaibhavwani.weatherapp.data.api.ApiState
import com.vaibhavwani.weatherapp.data.models.Weather
import kotlinx.coroutines.flow.Flow

/**
 * API entry point to the data layer
 */
interface WeatherRepository {
    fun fetchWeather(city: String): Flow<ApiState<Weather?>>
}
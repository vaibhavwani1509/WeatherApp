package com.vaibhavwani.weatherapp.data.repository

import android.util.Log
import com.vaibhavwani.weatherapp.data.api.ApiService
import com.vaibhavwani.weatherapp.data.api.ApiState
import com.vaibhavwani.weatherapp.main.repository.WeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation is kept in the data layer.
 * API / abstraction for this would be in the main layer which other modules can depend on.
 */
class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override fun fetchWeather(city: String) = flow {
        try {
            Log.d("Vaibhav", "fetchWeather: Started")

            val response = apiService.getWeather(city)
            Log.d("Vaibhav", "fetchWeather: $response")

            if (response.isSuccessful) {
                Log.d("Vaibhav", "fetchWeather: Success")
                emit(ApiState.Success(response.body()))
            } else {
                Log.d("Vaibhav", "fetchWeather: Error")
                emit(ApiState.Error("Error fetching the weather data"))
            }
        } catch (e: Exception) {
            Log.d("Vaibhav", "fetchWeather: Error $e")
            emit(ApiState.Error("Error fetching the weather data"))
        }
    }
}
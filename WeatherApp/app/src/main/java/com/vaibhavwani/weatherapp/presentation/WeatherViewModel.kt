package com.vaibhavwani.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavwani.weatherapp.data.api.ApiState
import com.vaibhavwani.weatherapp.data.models.Weather
import com.vaibhavwani.weatherapp.main.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model to handle functionalities on the weather screen
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _flow = MutableStateFlow<ApiState<Weather?>?>(null)
    val flow = _flow.asStateFlow()

    fun fetchWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchWeather(city)
                .onStart {
                    _flow.value = ApiState.Loading
                }
                .collect {
                    Log.d("Vaibhav", "fetchWeather: received")
                    _flow.value = it
                }
        }

    }

}
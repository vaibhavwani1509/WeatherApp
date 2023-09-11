package com.vaibhavwani.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vaibhavwani.weatherapp.data.api.ApiState
import com.vaibhavwani.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "Vaibhav"

/**
 * Main screen for the app
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flow.collect {
                    binding.progressCircular.visibility = View.GONE
                    binding.setState("Please search the city to fetch the weather")
                    it?.let {
                        when (it) {
                            is ApiState.Error -> {
                                Log.d(TAG, "onCreate: Error")
                                binding.progressCircular.visibility = View.GONE
                                binding.state.visibility = View.VISIBLE
                                binding.setState("Error fetching data")
                            }

                            ApiState.Loading -> {
                                Log.d(TAG, "onCreate: Loading")
                                binding.progressCircular.visibility = View.VISIBLE
                                binding.state.visibility = View.VISIBLE
                                binding.setState("Loading ...")
                            }

                            is ApiState.Success -> {
                                Log.d(TAG, "onCreate: Success")
                                binding.state.visibility = View.GONE

                                it.data?.let { weather ->
                                    binding.setCity("City: " + weather.location.name)
                                    weather.current.let { current ->
                                        binding.setTemperature("Temperature: " + (current.temperature.toString()) + " C")
                                            .run {
                                                binding.convertButton.visibility = View.VISIBLE
                                            }
                                        binding.setDescription("Description: " + current.weather_descriptions.joinToString())
                                        binding.setHumidity("Humidity: " + current.humidity.toString())
                                        binding.setWind("Wind Speed:: " + current.wind_speed.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.fetchWeatherButton.setOnClickListener {
            binding.searchCityEditBox.text.toString().takeIf { it.isNotEmpty() }?.let { city ->
                viewModel.fetchWeather(city)
            }
        }

        binding.convertButton.setOnClickListener {
            binding.setTemperature(binding.temperature.text?.toFahrenheit() ?: "")
        }
    }

    private fun CharSequence.toFahrenheit(): String {
        val data = split(" ")
        val currTemp = data[1].toDouble()
        var unit = data[2]
        val convertedTemp = if (unit == "C") {
            unit = "F"
            (currTemp * 9 / 5) + 32
        } else {
            unit = "C"
            (currTemp - 32) * 5 / 9
        }
        return "Temperature: ${convertedTemp.toInt()} $unit"
    }
}
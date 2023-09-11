package com.vaibhavwani.weatherapp.data.api

import com.vaibhavwani.weatherapp.data.models.Weather
import com.vaibhavwani.weatherapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch the weather
 */
interface ApiService {

    @GET("current?access_key=${API_KEY}")
    suspend fun getWeather(
        @Query("query") query: String
    ): Response<Weather>
}
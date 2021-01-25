package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.data.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApiService {

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroidsAsync(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): String

    @GET("/planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String
    ): ImageOfDayTransferObject
}

private val httpClient by lazy {
    OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(BODY))
        .build()
}

private val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .baseUrl(BASE_URL)
        .build()
}

object AsteroidApi {

    val asteroidService: AsteroidApiService = retrofit.create(AsteroidApiService::class.java)
}
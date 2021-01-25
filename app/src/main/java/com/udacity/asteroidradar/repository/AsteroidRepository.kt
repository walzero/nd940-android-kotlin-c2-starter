package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidApi.asteroidService
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.extensions.fromBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    private val key64 = "eFlrOGk5dnVHOGFsM2VHZ01yRXVnUmk5Y01HRGRkMjM5aGZsc0tNbw=="

    val nearbyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getNearbyAsteroids()) {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<ImageOfDay> =
        Transformations.map(database.asteroidDao.getImageOfDay()) {
            it.asDomainModel()
        }

    suspend fun refreshNearbyAsteroidsAsync() {
        val jsonResponse = asteroidService.getAsteroidsAsync(apiKey = key64.fromBase64())
        val nearbyAsteroids = parseAsteroidsJsonResult(JSONObject(jsonResponse))

        withContext(Dispatchers.IO) {
            database.asteroidDao.insertAll(*nearbyAsteroids.asDatabaseModel())
        }
    }

    suspend fun refreshImageOfTheDay() {
        val imageOfTheDayTransferObject = asteroidService.getImageOfTheDay(key64.fromBase64())

        withContext(Dispatchers.IO) {
            database.asteroidDao.insertAll(imageOfTheDayTransferObject.asDatabaseModel())
        }
    }
}
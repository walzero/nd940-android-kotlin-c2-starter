package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidApi.asteroidService
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.extensions.fromBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    private val key = "eFlrOGk5dnVHOGFsM2VHZ01yRXVnUmk5Y01HRGRkMjM5aGZsc0tNbw=="

    val nearbyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getNearbyAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroidsAsync() {
        val jsonResponse = asteroidService.getAsteroidsAsync(apiKey = key.fromBase64())
        val nearbyAsteroids = parseAsteroidsJsonResult(JSONObject(jsonResponse))
        nearbyAsteroids.asDatabaseModel()

        withContext(Dispatchers.IO) {
            database.asteroidDao.insertAll(*nearbyAsteroids.asDatabaseModel())
        }
    }
}
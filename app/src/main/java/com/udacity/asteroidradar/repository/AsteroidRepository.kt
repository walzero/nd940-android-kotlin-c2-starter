package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.extensions.fromBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository {
    private val key = "eFlrOGk5dnVHOGFsM2VHZ01yRXVnUmk5Y01HRGRkMjM5aGZsc0tNbw=="

//    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.videoDao.getVideos()) {
//        it.asDomainModel()
//    }

    suspend fun refreshAsteroidsAsync(): List<Asteroid> {
//        withContext(Dispatchers.IO) {
        val asteroids = AsteroidApi.asteroidService.getAsteroidsAsync(apiKey = key.fromBase64())
        val jsonAsteroid = JSONObject(asteroids)
        return parseAsteroidsJsonResult(jsonAsteroid)
//            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
//        }
    }
}
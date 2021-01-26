package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidApi.asteroidService
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.extensions.fromBase64
import com.udacity.asteroidradar.main.AsteroidFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidDatabase) {

    private val key = ""//TODO ADD YOUR API KEY HERE!

    private val dateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    }

    private val filterOption = MutableLiveData(AsteroidFilter.WEEK)

    val nearbyAsteroids: LiveData<List<Asteroid>> =
        Transformations.switchMap(filterOption) { filter ->
            val source = when (filter) {
                AsteroidFilter.DAY -> database.asteroidDao.getTodayNearbyAsteroids()
                else -> database.asteroidDao.getWeekNearbyAsteroids()
            }

            Transformations.map(source) {
                it?.asDomainModel()
            }
        }

    val imageOfTheDay: LiveData<ImageOfDay> =
        Transformations.map(database.asteroidDao.getImageOfDay()) {
            it?.asDomainModel()
        }

    fun setFilterOption(asteroidFilter: AsteroidFilter) {
        filterOption.postValue(asteroidFilter)
    }

    suspend fun refreshNearbyAsteroidsAsync() {
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
        val endDate = dateFormat.format(calendar.time)

        val jsonResponse = asteroidService.getAsteroidsAsync(
            apiKey = key,
            startDate = startDate,
            endDate = endDate
        )
        val nearbyAsteroids = parseAsteroidsJsonResult(JSONObject(jsonResponse))

        withContext(Dispatchers.IO) {
            database.asteroidDao.insertAll(*nearbyAsteroids.asDatabaseModel())
        }
    }

    suspend fun refreshImageOfTheDay() {
        val imageOfTheDayTransferObject = asteroidService.getImageOfTheDay(key)

        withContext(Dispatchers.IO) {
            database.asteroidDao.insertAll(imageOfTheDayTransferObject.asDatabaseModel())
        }
    }
}
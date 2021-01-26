package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.database.getAsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getAsteroidDatabase(application)

    private val asteroidRepository = AsteroidRepository(database)

    var nearbyAsteroids: LiveData<List<Asteroid>> = asteroidRepository.nearbyAsteroids

    val imageOfDay: LiveData<ImageOfDay> = asteroidRepository.imageOfTheDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    init {
        launchRefreshNearbyAsteroids()
        launchRefreshImageOfDay()
    }

    fun changeFilter(asteroidFilter: AsteroidFilter) {
        asteroidRepository.setFilterOption(asteroidFilter)
    }

    private fun launchRefreshNearbyAsteroids() = viewModelScope.launch {
        try {
            asteroidRepository.refreshNearbyAsteroidsAsync()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun launchRefreshImageOfDay() = viewModelScope.launch {
        try {
            asteroidRepository.refreshImageOfTheDay()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun doneNavigatingToAsteroidDetail() {
        _navigateToAsteroidDetail.value = null
    }
}

enum class AsteroidFilter {
    WEEK,
    DAY
}
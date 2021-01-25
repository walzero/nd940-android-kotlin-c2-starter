package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.getAsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getAsteroidDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    val nearbyAsteroids = asteroidRepository.nearbyAsteroids

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    init {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroidsAsync()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun doneNavigatingToAsteroidDetail() {
        _navigateToAsteroidDetail.value = null
    }
}
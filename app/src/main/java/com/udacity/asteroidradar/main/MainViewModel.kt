package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.data.Asteroid
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        setTestValues()
    }


    fun setTestValues() {
        val list = listOf(
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            ),
            Asteroid(
                Random.nextLong(),
                "test asteroid",
                "1991-11-06",
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextBoolean()
            )
        )

        _asteroids.value = list
    }
}
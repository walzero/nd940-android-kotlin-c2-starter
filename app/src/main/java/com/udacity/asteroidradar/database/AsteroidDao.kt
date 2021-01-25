package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("select * from nearby_asteroids")
    fun getNearbyAsteroids(): LiveData<List<AsteroidDataModel>>

    @Query("select * from image_of_day")
    fun getImageOfDay(): LiveData<ImageOfDayDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg imageOfDay: ImageOfDayDataModel)
}
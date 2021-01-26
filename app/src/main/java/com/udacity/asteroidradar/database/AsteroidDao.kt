package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM nearby_asteroids WHERE close_approach_date >= date('now', 'start of day') ORDER BY close_approach_date DESC")
    fun getNearbyAsteroids(): LiveData<List<AsteroidDataModel>>

    @Query("SELECT * FROM image_of_day ORDER BY date DESC LIMIT 1")
    fun getImageOfDay(): LiveData<ImageOfDayDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidDataModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg imageOfDay: ImageOfDayDataModel)

    @Delete
    fun deleteAll(vararg asteroidDataModel: AsteroidDataModel)

    @Delete
    fun deleteAll(vararg imageOfDayDataModel: ImageOfDayDataModel)

    @Update
    fun updateAll(vararg asteroidDataModel: AsteroidDataModel)

    @Update
    fun updateAll(vararg imageOfDayDataModel: ImageOfDayDataModel)

    @Query("DELETE FROM nearby_asteroids")
    fun clearNearbyAsterois()

    @Query("DELETE FROM image_of_day")
    fun clearImagesOfTheDay()
}
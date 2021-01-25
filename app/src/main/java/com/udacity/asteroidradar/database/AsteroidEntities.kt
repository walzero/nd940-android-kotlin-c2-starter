package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay

@Entity(tableName = "nearby_asteroids")
data class AsteroidDataModel constructor(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "codename")
    val codename: String = "",

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String = "",

    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double = 0.0,

    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double = 0.0,

    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double = 0.0,

    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double = 0.0,

    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean = false
)

@Entity(tableName = "image_of_day")
data class ImageOfDayDataModel constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "explanation")
    val explanation: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "hdurl")
    val hdurl: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "isImage")
    val isImage: Boolean = false
)

fun List<AsteroidDataModel>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun ImageOfDayDataModel.asDomainModel(): ImageOfDay {
    return ImageOfDay(
        title = title,
        explanation = explanation,
        url = url,
        hdurl = hdurl,
        isImage = isImage,
        date = date
    )
}
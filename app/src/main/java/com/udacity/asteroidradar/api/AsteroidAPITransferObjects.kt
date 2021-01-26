package com.udacity.asteroidradar.api

import com.squareup.moshi.*
import com.udacity.asteroidradar.api.ImageOfDayTransferObject.Companion.MEDIA_TYPE_IMAGE
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.database.AsteroidDataModel
import com.udacity.asteroidradar.database.ImageOfDayDataModel
import java.io.IOException


data class AsteroidTransferObject(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@JsonClass(generateAdapter = true)
data class ImageOfDayTransferObject(

    @Json(name = "copyright")
    val copyright: String = "",

    @Json(name = "date")
    val date: String = "",

    @Json(name = "explanation")
    val explanation: String = "",

    @Json(name = "hdurl")
    val hdurl: String = "",

    @Json(name = "media_type")
    val media_type: String = "",

    @Json(name = "service_version")
    val service_version: String = "",

    @Json(name = "title")
    val title: String = "",

    @Json(name = "url")
    val url: String = ""
) {
    companion object {
        const val MEDIA_TYPE_IMAGE = "image"
    }
}

fun ArrayList<AsteroidTransferObject>.asDomainModel(): List<Asteroid> {
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
    }.toList()
}

fun ArrayList<AsteroidTransferObject>.asDatabaseModel(): Array<AsteroidDataModel> {
    return map {
        AsteroidDataModel(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun ImageOfDayTransferObject.asDomainModel(): ImageOfDay {
    return ImageOfDay(
        title = title,
        explanation = explanation,
        url = url,
        hdurl = hdurl,
        isImage = MEDIA_TYPE_IMAGE == media_type,
        date = date
    )
}

fun ImageOfDayTransferObject.asDatabaseModel(): ImageOfDayDataModel {
    return ImageOfDayDataModel(
        title = title,
        explanation = explanation,
        url = url,
        hdurl = hdurl,
        isImage = MEDIA_TYPE_IMAGE == media_type,
        date = date
    )
}

val VOID_JSON_ADAPTER: Any = object : Any() {
    @FromJson
    @Throws(IOException::class)
    fun fromJson(reader: JsonReader): Void? {
        return reader.nextNull()
    }

    @ToJson
    @Throws(IOException::class)
    fun toJson(writer: JsonWriter, v: Void?) {
        writer.nullValue()
    }
}
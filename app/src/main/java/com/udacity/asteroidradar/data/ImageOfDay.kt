package com.udacity.asteroidradar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ImageOfDay(
    val title: String = "",
    val explanation: String = "",
    val url: String = "",
    val hdurl: String = "",
    val date: String = "",
    val isImage: Boolean = false
) : Parcelable {
    fun getId(): Long = url.hashCode().toLong()
}
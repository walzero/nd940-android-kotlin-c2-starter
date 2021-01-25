package com.udacity.asteroidradar.extensions

import android.util.Base64.decode
import android.util.Base64

fun String.fromBase64(): String = try {
    String(decode(this, Base64.DEFAULT))
} catch (e: IllegalArgumentException) {
    this
}
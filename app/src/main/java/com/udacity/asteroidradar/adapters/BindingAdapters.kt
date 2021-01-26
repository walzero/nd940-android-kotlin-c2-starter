package com.udacity.asteroidradar.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    with(imageView.context) {
        if (isHazardous) {
            imageView.setImageResource(R.drawable.asteroid_hazardous)
            imageView.contentDescription = getString(R.string.potentially_hazardous_asteroid_image)
        } else {
            imageView.setImageResource(R.drawable.asteroid_safe)
            imageView.contentDescription = getString(R.string.not_hazardous_asteroid_image)
        }
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.addHeaderAndSubmitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imageOfDay: ImageOfDay?) {
    with(imgView.context) {
        when (imageOfDay) {
            null -> {
                imgView.setImageResource(android.R.color.transparent)
                imgView.contentDescription = getString(R.string.image_of_the_day)
            }

            else -> {
                imgView.contentDescription = imageOfDay.title
                val imgUri = imageOfDay.url.toUri().buildUpon().scheme("https").build()

                Picasso.with(this@with)
                    .load(imgUri)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(imgView)
            }
        }
    }
}

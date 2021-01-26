package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.databinding.AsteroidHeaderBinding
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AsteroidListAdapter(
    private val onclickListener: AsteroidListener
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(AsteroidDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private var imageOfDay: ImageOfDay? = null

    fun addHeaderAndSubmitList(asteroids: List<Asteroid>?) {
        adapterScope.launch {
            val items = when (asteroids) {
                null -> listOf(ListItem.Header(imageOfDay))
                else -> listOf(ListItem.Header(imageOfDay)) + asteroids.map {
                    ListItem.AsteroidItem(it)
                }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun updateImageOfTheDay(img: ImageOfDay?) {
        when {
            this.imageOfDay == null && itemCount > 0 -> {
                this.imageOfDay = img
                updateHeaderImageReference()
            }
            else -> {
                this.imageOfDay = img
            }
        }
    }

    private fun updateHeaderImageReference() {
        try {
            val header = getItem(0) as ListItem.Header
            header.imageOfDay = this.imageOfDay
            notifyItemChanged(0)
        } catch (e: IndexOutOfBoundsException) {
            Timber.e(e)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewType.HEADER.id
            else -> ViewType.ASTEROID.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.id -> HeaderViewHolder.from(parent)
            ViewType.ASTEROID.id -> AsteroidViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> bindHeaderViewHolder(holder)
            is AsteroidViewHolder -> bindAsteroidViewHolder(holder, position)
            else -> throw ClassCastException("Unknown viewholder $holder")
        }
    }

    private fun bindHeaderViewHolder(holder: HeaderViewHolder) {
        holder.bind(imageOfDay)
    }

    private fun bindAsteroidViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position) as ListItem.AsteroidItem
        holder.itemView.setOnClickListener {
            onclickListener.onClick(item.asteroid)
        }
        holder.bind(item.asteroid)
    }

    class AsteroidViewHolder(
        private var binding: AsteroidItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(
        private var binding: AsteroidHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageOfDay: ImageOfDay?) {
            binding.imageOfDay = imageOfDay
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    enum class ViewType(val id: Int) {
        HEADER(id = 1),
        ASTEROID(id = 2)
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class AsteroidListener(val clickListener: (marsProperty: Asteroid) -> Unit) {
    fun onClick(marsProperty: Asteroid) = clickListener(marsProperty)
}

sealed class ListItem {
    abstract val id: Long

    data class AsteroidItem(val asteroid: Asteroid) : ListItem() {
        override val id = asteroid.id
    }

    data class Header(var imageOfDay: ImageOfDay?) : ListItem() {
        override val id = imageOfDay?.getId() ?: Long.MIN_VALUE
    }
}
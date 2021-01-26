package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidListAdapter
import com.udacity.asteroidradar.adapters.AsteroidListener
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var asteroidAdapter: AsteroidListAdapter

    private val displayAsteroidDetailObserver by lazy {
        Observer<Asteroid?> { asteroid ->
            asteroid?.let { navigateToAsteroidDetails(it) }
        }
    }

    private val imageOfTheDayObserver by lazy {
        Observer<ImageOfDay?> { image ->
            if (::asteroidAdapter.isInitialized) {
                asteroidAdapter.updateImageOfTheDay(image)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        asteroidAdapter =
            AsteroidListAdapter(AsteroidListener { viewModel.displayAsteroidDetails(it) })

        binding.asteroidRecycler.adapter = asteroidAdapter

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids -> {
                viewModel.changeFilter(AsteroidFilter.WEEK)
                return true
            }

            R.id.show_todays_asteroids -> {
                viewModel.changeFilter(AsteroidFilter.DAY)
                return true
            }

            R.id.show_saved_asteroids -> {
                showNotImplementedToast()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        viewModel.navigateToAsteroidDetail.observe(this, displayAsteroidDetailObserver)
        viewModel.imageOfDay.observe(this, imageOfTheDayObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.navigateToAsteroidDetail.removeObserver(displayAsteroidDetailObserver)
        viewModel.imageOfDay.removeObserver(imageOfTheDayObserver)
    }

    private fun navigateToAsteroidDetails(asteroid: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        viewModel.doneNavigatingToAsteroidDetail()
    }

    private fun showNotImplementedToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.not_yet_implemented),
            Toast.LENGTH_SHORT
        ).show()
    }
}

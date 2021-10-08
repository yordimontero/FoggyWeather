package com.circleappsstudio.foggyweather.ui.current

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.*
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.databinding.FragmentCurrentWeatherBinding
import com.circleappsstudio.foggyweather.presenter.LocationViewModel
import com.circleappsstudio.foggyweather.presenter.LocationViewModelFactory
import com.circleappsstudio.foggyweather.presenter.WeatherViewModel
import com.circleappsstudio.foggyweather.presenter.WeatherViewModelFactory
import com.circleappsstudio.foggyweather.repository.weather.RetrofitClient
import com.circleappsstudio.foggyweather.repository.weather.WeatherRepositoryImpl
import com.circleappsstudio.foggyweather.repository.location.Location
import com.circleappsstudio.foggyweather.repository.location.LocationRepositoryImpl
import com.circleappsstudio.foggyweather.ui.current.adapter.Forecast3DaysAdapter
import com.circleappsstudio.foggyweather.ui.current.adapter.ForecastByHourAdapter
import java.util.*

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private lateinit var binding: FragmentCurrentWeatherBinding

    private val viewModel by viewModels<WeatherViewModel> {
        WeatherViewModelFactory(
            WeatherRepositoryImpl(
                WeatherRemoteDataSource(
                    RetrofitClient.webService
                )
            )
        )
    }

    private val locationViewModel by viewModels<LocationViewModel> {
        LocationViewModelFactory(
            LocationRepositoryImpl(
                Location()
            )
        )
    }

    private val currentDate by lazy {
        formatDate(Calendar.getInstance().time)
    }

    private var cordenades: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCurrentWeatherBinding.bind(view)

        requestLocationPermissions()

        showOrHideSearchView()

        setupSearchView()

    }

    private fun getLocationObserver() {

        locationViewModel.fetchLocation(requireContext())
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {

                        cordenades = "${resultEmitted.data[0]},${resultEmitted.data[1]}"

                        getCurrentWeatherObserver(location = cordenades)
                        getForecastObserver(location = cordenades)
                        getAstronomyObserver(location = cordenades, date = currentDate)
                        getForecast3DaysObserver(location = cordenades)

                        binding.progressBar.visibility = View.GONE

                    }

                    is Result.Failure -> {

                        Toast.makeText(
                            requireContext(),
                            "Something went wrong: ${resultEmitted.exception.message.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.progressBar.visibility = View.GONE

                    }

                }

            })

    }

    private fun getCurrentWeatherObserver(location: String, airQuality: Boolean = false) {

        viewModel.fetchCurrentWeather(
            location,
            airQuality
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {

                    binding.txtLocation.text =
                        "${resultEmitted.data.location.name}, ${resultEmitted.data.location.region}"

                    binding.txtTemperature.text = "${resultEmitted.data.current.temp_c}°C"

                    Glide.with(requireContext())
                        .load("${AppConstants.BASE_IMAGE_URL}${resultEmitted.data.current.condition.icon}")
                        .centerCrop()
                        .into(binding.imgIcon)

                    binding.txtCondition.text = resultEmitted.data.current.condition.text

                    binding.txtFeelsLike.text = "${resultEmitted.data.current.feelslike_c}°C"

                    val hour = splitDate(resultEmitted.data.current.last_updated)

                    val formattedHour = formatHour(
                        splitHour(hour, 0),
                        splitHour(hour, 1),
                        requireContext()
                    )

                    binding.txtLastUpdate.text = formattedHour

                    binding.progressBar.visibility = View.GONE

                }

                is Result.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.progressBar.visibility = View.GONE

                }

            }

        })

    }

    private fun getForecastObserver(
        location: String,
        days: Int = 1,
        airQuality: Boolean = false,
        alerts: Boolean = false
    ) {

        viewModel.fetchForecast(
            location,
            days,
            airQuality,
            alerts
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {

                    resultEmitted.data.forecast.forecastday.forEachIndexed { index, forecastDay ->

                        if (index == 0) {
                            binding.txtMaxTemp.text = "${forecastDay.day.maxtemp_c}°C"
                            binding.txtMinTemp.text = "${forecastDay.day.mintemp_c}°C"
                        }

                    }

                    val currentHour = getCurrentHourFormatted(requireContext())

                    val forecastRecyclerViewPosition = getCurrentForecastCard(
                        currentHour, requireContext()
                    )

                    resultEmitted.data.forecast.forecastday.forEachIndexed { index, forecastDay ->

                        binding.rvForecast.adapter = ForecastByHourAdapter(
                            forecastDay.hour
                        )

                        binding.rvForecast.scrollToPosition(forecastRecyclerViewPosition)

                    }

                    binding.progressBar.visibility = View.GONE

                }

                is Result.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.progressBar.visibility = View.GONE

                }

            }

        })

    }

    private fun getForecast3DaysObserver(
        location: String,
        days: Int = 3,
        airQuality: Boolean = false,
        alerts: Boolean = false
    ) {

        viewModel.fetchForecast(
            location,
            days,
            airQuality,
            alerts
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {

                    binding.rvForecast3Days.adapter =
                        Forecast3DaysAdapter(resultEmitted.data.forecast.forecastday)

                    binding.progressBar.visibility = View.GONE

                }

                is Result.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.progressBar.visibility = View.GONE

                }

            }

        })

    }


    private fun getAstronomyObserver(
        location: String,
        date: String
    ) {

        viewModel.fetchAstronomy(location, date)
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {

                        binding.txtSunrise.text = resultEmitted.data.astronomy.astro.sunrise
                        binding.txtSunset.text = resultEmitted.data.astronomy.astro.sunset

                        binding.txtMoonrise.text = resultEmitted.data.astronomy.astro.moonrise
                        binding.txtMoonset.text = resultEmitted.data.astronomy.astro.moonset

                        binding.txtMoonPhase.text = resultEmitted.data.astronomy.astro.moon_phase
                        binding.txtMoonIllumination.text =
                            resultEmitted.data.astronomy.astro.moon_illumination

                        binding.progressBar.visibility = View.GONE

                    }

                    is Result.Failure -> {

                        Toast.makeText(
                            requireContext(),
                            "Something went wrong: ${resultEmitted.exception.message.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.wtf("TAG", resultEmitted.exception.message.toString())

                        binding.progressBar.visibility = View.GONE

                    }

                }

            })

    }

    private fun showOrHideSearchView() {

        binding.txtLocation.setOnClickListener {

            if (binding.searchViewLayout.visibility == View.GONE) {
                showSearchView()
            } else {
                hideSearchView()
            }

        }

    }

    private fun showSearchView() {
        binding.searchViewLayout.visibility = View.VISIBLE
    }

    private fun hideSearchView() {
        binding.searchViewLayout.visibility = View.GONE
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {

                text?.let {

                    getCurrentWeatherObserver(location = it)
                    getForecastObserver(location = it)
                    getAstronomyObserver(location = it, date = currentDate)
                    getForecast3DaysObserver(location = it)

                    hideSearchView()

                }

                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {

                /*if (text!! == "...") {
                    // Do some magic.
                } else {
                    // Do some magic.
                }*/

                return false
            }

        })

    }

    private fun requestLocationPermissions() {

        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), AppConstants.LOCATION_REQUEST_CODE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AppConstants.LOCATION_REQUEST_CODE) {

            if (checkLocationPermissions(requireContext())) {
                getLocationObserver()
            } else {
                Toast.makeText(requireContext(), "Location permission not granted!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

}
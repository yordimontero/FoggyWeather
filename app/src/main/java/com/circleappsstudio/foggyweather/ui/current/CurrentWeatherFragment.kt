package com.circleappsstudio.foggyweather.ui.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.*
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.databinding.FragmentCurrentWeatherBinding
import com.circleappsstudio.foggyweather.presenter.WeatherViewModel
import com.circleappsstudio.foggyweather.presenter.WeatherViewModelFactory
import com.circleappsstudio.foggyweather.repository.RetrofitClient
import com.circleappsstudio.foggyweather.repository.WeatherRepositoryImpl
import com.circleappsstudio.foggyweather.ui.current.adapter.ForecastAdapter
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

    private val currentDate by lazy {
        formatDate(Calendar.getInstance().time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCurrentWeatherBinding.bind(view)

        getCurrentWeatherObserver("Grecia", false)
        getForecastObserver("Grecia", 1, false, false)
        getAstronomyObserver("Grecia", currentDate)

        Log.wtf("TAG", currentDate)

    }


    private fun getCurrentWeatherObserver(location: String, airQuality: Boolean) {

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

                    binding.txtLastUpdate.text = splitDate(resultEmitted.data.current.last_updated)

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
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
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

                        binding.rvForecast.adapter = ForecastAdapter(
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
                        binding.txtMoonIllumination.text = resultEmitted.data.astronomy.astro.moon_illumination

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

}
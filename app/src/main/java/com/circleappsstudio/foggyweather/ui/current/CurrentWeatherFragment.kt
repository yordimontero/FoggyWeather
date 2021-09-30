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
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.databinding.FragmentCurrentWeatherBinding
import com.circleappsstudio.foggyweather.presenter.WeatherViewModel
import com.circleappsstudio.foggyweather.presenter.WeatherViewModelFactory
import com.circleappsstudio.foggyweather.repository.RetrofitClient
import com.circleappsstudio.foggyweather.repository.WeatherRepositoryImpl

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCurrentWeatherBinding.bind(view)

        getCurrentWeatherObserver("Grecia", false)
        //getCurrentWeatherLocationObserver("Grecia", false)

    }

    private fun getCurrentWeatherObserver(location: String, airQuality: Boolean) {

        viewModel.fetchCurrentWeather(
            location,
            airQuality
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {

                    Toast.makeText(
                        requireContext(),
                        "Loading...",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is Result.Success -> {

                    binding.txtLocation.text = "${resultEmitted.data.location.name}, ${resultEmitted.data.location.region}"

                    binding.txtTemperature.text = "${resultEmitted.data.current.temp_c}°C"

                    Glide.with(requireContext())
                        .load("${AppConstants.BASE_IMAGE_URL}${resultEmitted.data.current.condition.icon}")
                        .centerCrop()
                        .into(binding.imgIcon)

                    binding.txtCondition.text = resultEmitted.data.current.condition.text



                    binding.txtFeelsLike.text = "${resultEmitted.data.current.feelslike_c}°C"
                    binding.txtLastUpdate.text = resultEmitted.data.current.last_updated


                    /*Log.wtf(
                        "TAG", "Current Weather:\n" +
                            "Last Updated: ${resultEmitted.data.current.last_updated}\n" +
                            "Temperature: ${resultEmitted.data.current.temp_c}\n" +
                            "Feels like: ${resultEmitted.data.current.feelslike_c}"
                    )*/

                }

                is Result.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        })

    }

    /*private fun getCurrentWeatherLocationObserver(location: String, airQuality: Boolean) {

        viewModel.fetchCurrentWeatherLocation(
            location,
            airQuality
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {

                    Toast.makeText(
                        requireContext(),
                        "Loading...",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is Result.Success -> {

                    binding.txtLocation.text = "${resultEmitted.data.location.name}, ${resultEmitted.data.location.region}"

                }

                is Result.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        })

    }*/


}
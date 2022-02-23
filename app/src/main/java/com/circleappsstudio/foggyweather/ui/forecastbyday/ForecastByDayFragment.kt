package com.circleappsstudio.foggyweather.ui.forecastbyday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.core.time.getCurrentForecastCard
import com.circleappsstudio.foggyweather.core.time.getCurrentHourFormatted
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.FragmentForecastByDayBinding
import com.circleappsstudio.foggyweather.presenter.WeatherViewModel
import com.circleappsstudio.foggyweather.ui.forecastbyday.adapter.ForecastByDayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastByDayFragment : Fragment(R.layout.fragment_forecast_by_day) {

    private lateinit var binding: FragmentForecastByDayBinding
    private lateinit var navController: NavController

    private val weatherViewModel by viewModels<WeatherViewModel>()

    private var date: String = ""
    private var location: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastByDayBinding.bind(view)
        navController = Navigation.findNavController(view)

        getBundle()

        fetchAllWeatherDataObserver(
            location,
            false,
            1,
            false,
            date
        )

    }

    private fun getBundle() {
        arguments?.let { bundle ->
            date = bundle.getString("date").toString()
            location = bundle.getString("location").toString()
        }
    }

    private fun fetchAllWeatherDataObserver(
        location: String,
        airQuality: Boolean,
        days: Int,
        alerts: Boolean,
        date: String
    ) {
        /*
            Method to fetch all weather data.
        */
        weatherViewModel.fetchAllWeatherData(
            location, airQuality, days, alerts, date
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->

            when (resultEmitted) {

                is Result.Loading -> {
                    //showMainProgressbar()
                    requireContext().showToast(
                        requireContext(),
                        "Loading"
                    )
                }

                is Result.Success -> {
                    /*
                        t1 = getCurrentWeather.
                        t2 = getForecast.
                        t3 = getAstronomy.
                    */

                    // t2 - getForecast:
                    getForecastUISetup(
                        forecastDayList = resultEmitted.data.t2.forecast.forecastday
                    )

                    //hideMainProgressbar()

                }

                is Result.Failure -> {

                    requireContext().showToast(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message}"
                    )

                    //binding.mainLayout.hide()
                    //binding.progressBar.hide()
                }

            }

        })

    }

    private fun getForecastUISetup(
        forecastDayList: List<ForecastDay>,
    ) {
        /*
            Method to setup Forecast UI section.
        */

        val currentHour = getCurrentHourFormatted(requireContext())

        val forecastRecyclerViewPosition = getCurrentForecastCard(
            currentHour, requireContext()
        )

        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == 0) {

                binding.rvForecast.adapter = ForecastByDayAdapter(
                    forecastDay.hour,
                    forecastRecyclerViewPosition.toString(),
                    requireContext()
                )

            }

        }

        binding.rvForecast.scrollToPosition(forecastRecyclerViewPosition)

    }

}
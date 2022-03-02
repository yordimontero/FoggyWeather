package com.circleappsstudio.foggyweather.ui.forecastbyday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.FragmentForecastByDayBinding
import com.circleappsstudio.foggyweather.ui.forecastbyday.adapter.ForecastByDayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastByDayFragment : Fragment(R.layout.fragment_forecast_by_day) {

    private lateinit var binding: FragmentForecastByDayBinding
    private lateinit var navController: NavController

    private var forecastDayList: List<ForecastDay> = listOf()
    private var forecast3DaysAdapterPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastByDayBinding.bind(view)
        navController = Navigation.findNavController(view)

        getBundle()

        checkIfForecastDayListIsEmpty()

    }

    private fun getBundle() {
        arguments?.let { bundle ->
            forecastDayList = bundle.get("forecastList") as List<ForecastDay>
            forecast3DaysAdapterPosition = bundle.getInt("position")
        }
    }

    private fun checkIfForecastDayListIsEmpty() {

        if (forecastDayList.isEmpty()) {
            requireContext().showToast(requireContext(), "Error fetching forecast data")
            return
        }

        getForecastUISetup(forecastDayList)

    }

    private fun getForecastUISetup(forecastDayList: List<ForecastDay>) {

        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == forecast3DaysAdapterPosition) {
                binding.rvForecast.adapter = ForecastByDayAdapter(forecastDay.hour)
            }

        }

    }

}
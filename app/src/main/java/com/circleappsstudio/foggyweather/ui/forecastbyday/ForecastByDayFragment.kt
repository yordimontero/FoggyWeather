package com.circleappsstudio.foggyweather.ui.forecastbyday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.time.convertStringToDate
import com.circleappsstudio.foggyweather.core.time.getDateWithMonthName
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.FragmentForecastByDayBinding
import com.circleappsstudio.foggyweather.ui.forecastbyday.adapter.ForecastByDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ForecastByDayFragment : Fragment(R.layout.fragment_forecast_by_day) {

    private lateinit var binding: FragmentForecastByDayBinding
    private lateinit var navController: NavController
    
    private var forecastDayList: List<ForecastDay> = listOf()
    private var forecast3DaysAdapterPosition: Int = 0
    private var dateString: String = ""
    private lateinit var date: Date

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
            dateString = bundle.getString("date").toString()
        }
    }
    
    private fun setDateTextView() {

        date = convertStringToDate(dateString)

        val dateTest = getDateWithMonthName(
            date
        )

        binding.txtDate.text = dateTest

    }

    private fun checkIfForecastDayListIsEmpty() {

        if (forecastDayList.isEmpty()) {
            requireContext().showToast(requireContext(), "Error fetching forecast data")
            return
        }

        getForecastUISetup(forecastDayList)

    }

    private fun getForecastUISetup(forecastDayList: List<ForecastDay>) {

        setDateTextView()
        
        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == forecast3DaysAdapterPosition) {
                binding.rvForecast.adapter = ForecastByDayAdapter(forecastDay.hour)
            }

        }

    }

}
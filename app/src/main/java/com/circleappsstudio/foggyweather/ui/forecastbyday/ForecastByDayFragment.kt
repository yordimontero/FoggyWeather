package com.circleappsstudio.foggyweather.ui.forecastbyday

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.Result
import androidx.lifecycle.Observer
import com.circleappsstudio.foggyweather.core.time.convertStringToDate
import com.circleappsstudio.foggyweather.core.time.getDateWithMonthName
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.FragmentForecastByDayBinding
import com.circleappsstudio.foggyweather.presenter.AdMobUtilsViewModel
import com.circleappsstudio.foggyweather.ui.forecastbyday.adapter.ForecastByDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ForecastByDayFragment : Fragment(R.layout.fragment_forecast_by_day) {

    private lateinit var binding: FragmentForecastByDayBinding
    private lateinit var navController: NavController
    
    private var forecastDayList: List<ForecastDay>? = listOf()
    private var forecast3DaysAdapterPosition: Int = 0
    private var dateString: String = ""
    private lateinit var date: Date

    private val adMobViewModel by viewModels<AdMobUtilsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastByDayBinding.bind(view)
        navController = Navigation.findNavController(view)

        showInterstitial()
        loadBanner()

        getBundle()

        checkIfForecastDayListIsEmpty()

    }

    private fun getBundle() {

        arguments?.let { bundle ->

            forecastDayList = bundle.get("forecastList") as List<ForecastDay>?

            if (forecastDayList == null) {
                forecastDayList = listOf()
            }

            forecast3DaysAdapterPosition = bundle.getInt("position")

            dateString = bundle.getString("date").toString()

        }

    }
    
    private fun setDateTextView() {

        date = convertStringToDate(dateString)

        val dateTest = getDateWithMonthName(date)

        binding.txtDate.text = dateTest

    }

    private fun checkIfForecastDayListIsEmpty() {

        if (forecastDayList?.isNullOrEmpty() == true) {
            hideDate()
            hideRecyclerViewForecast()
            showNoDataAvailable()
            requireContext().showToast(requireContext(), "Error fetching forecast data")
            return
        }

        forecastDayList?.let {
            getForecastUISetup(it)
        }

    }

    private fun getForecastUISetup(forecastDayList: List<ForecastDay>) {

        setDateTextView()
        
        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == forecast3DaysAdapterPosition) {
                binding.rvForecast.adapter = ForecastByDayAdapter(forecastDay.hour)
            }

        }

    }

    private fun loadBanner() {
        /*
            Método para cargar el anuncio Banner "BannerMain".
        */
        binding.bannerMain.loadAd(
            adMobViewModel.loadBannerAdMob()
        )

    }

    private fun showInterstitial() {
        /*
            Método para mostrar el anuncio Intersticial "Interstitial_Main".
        */

        showProgressBar()

        adMobViewModel.showInterstitialAdMob()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showProgressBar()
                    }

                    is Result.Success -> {

                        val ad = resultEmitted.data

                        ad?.show(requireActivity())

                        hideProgressBar()

                    }

                    is Result.Failure -> {
                        hideProgressBar()
                    }

                }

            })
    }


    private fun hideDate() {
        binding.txtDate.hide()
    }

    private fun hideRecyclerViewForecast() {
        binding.rvForecast.hide()
    }

    private fun showNoDataAvailable() {
        binding.txtNoDataAvailable.show()
    }

    private fun showProgressBar() {
        binding.progressBar.show()
    }

    private fun hideProgressBar() {
        binding.progressBar.hide()
    }

}
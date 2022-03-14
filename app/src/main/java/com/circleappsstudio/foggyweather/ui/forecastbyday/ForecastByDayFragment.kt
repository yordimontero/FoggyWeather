package com.circleappsstudio.foggyweather.ui.forecastbyday

import android.os.Bundle
import android.os.Parcelable
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
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnInternetCheckDialogClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.internetCheckDialog
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.data.model.ForecastByDay
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.FragmentForecastByDayBinding
import com.circleappsstudio.foggyweather.presenter.AdMobUtilsViewModel
import com.circleappsstudio.foggyweather.presenter.InternetCheckViewModel
import com.circleappsstudio.foggyweather.ui.forecastbyday.adapter.ForecastByDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.Serializable
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ForecastByDayFragment: Fragment(R.layout.fragment_forecast_by_day),
    OnInternetCheckDialogClickListener {

    private lateinit var binding: FragmentForecastByDayBinding
    private lateinit var navController: NavController

    private val internetCheckViewModel by viewModels<InternetCheckViewModel>()
    private val adMobUtilsViewModel by viewModels<AdMobUtilsViewModel>()
    
    private var forecastDayList: List<ForecastDay>? = listOf()
    private var forecastAdapterPosition: Int = 0

    private var dateString: String = ""
    private lateinit var date: Date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastByDayBinding.bind(view)
        navController = Navigation.findNavController(view)

        checkInternetObserver()

    }

    private fun checkInternetObserver() {
        /*
            Method to check if there's internet connection.
        */
        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showProgressBar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {
                            showInterstitialAdObserver()
                        } else {
                            showInternetCheckDialog()
                        }

                    }

                    is Result.Failure -> {
                        showInternetCheckDialog()
                    }

                }

            })

    }

    private fun getBundle() {
        /*
            Method to get data from Bundle.
        */
        arguments?.let { bundle ->

            forecastDayList = bundle.get("forecastList") as List<ForecastDay>?

            if (forecastDayList == null) {
                forecastDayList = listOf()
            }

            forecastAdapterPosition = bundle.getInt("position")

            dateString = bundle.getString("date").toString()

            // Testing:
            bundle.remove("forecastList")
            bundle.remove("position")
            bundle.remove("date")

        }

    }

    private fun checkIfForecastDayListIsNullOrEmpty() {
        /*
            Method to check if forecastDayList is null or empty.
        */
        if (forecastDayList?.isNullOrEmpty() == true) {
            hideDate()
            hideRecyclerViewForecast()
            showTextViewErrorMessage()
            return
        }

        forecastDayList?.let {
            getForecastUISetup(it)
        }

    }

    private fun getForecastUISetup(forecastDayList: List<ForecastDay>) {
        /*
            Method to prepare UI to display forecast data.
        */
        setDateTextView()

        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == forecastAdapterPosition) {
                binding.rvForecast.adapter = ForecastByDayAdapter(forecastDay.hour)
            }

        }

    }

    private fun showBannerAd() {
        /*
            Method to show an BannerAd.
        */
        adMobUtilsViewModel.showBannerAd(
            binding.bannerMain
        )
    }

    private fun showInterstitialAdObserver() {
        /*
            Method to show an InterstitialAd.
        */
        adMobUtilsViewModel.showInterstitialAd()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showProgressBar()
                    }

                    is Result.Success -> {

                        val ad = resultEmitted.data
                        ad?.show(requireActivity())

                        getBundle()
                        checkIfForecastDayListIsNullOrEmpty()
                        showBannerAd()

                        hideProgressBar()

                    }

                    is Result.Failure -> {
                        hideProgressBar()
                    }

                }

            })
    }

    private fun setDateTextView() {
        /*
            Method to set date with month name.
        */
        convertStringToDate(dateString)?.let {
            date = it
            binding.txtDate.text = getDateWithMonthName(date)
        }

    }

    private fun showProgressBar() {
        /*
            Method to show a ProgressBar.
        */
        binding.progressBar.show()
    }

    private fun hideProgressBar() {
        /*
            Method to hide a ProgressBar.
        */
        binding.progressBar.hide()
    }

    private fun hideDate() {
        /*
            Method to hide date TextView.
        */
        binding.txtDate.hide()
    }

    private fun hideRecyclerViewForecast() {
        /*
            Method to hide ForecastRecyclerView.
        */
        binding.rvForecast.hide()
    }

    private fun showTextViewErrorMessage() {
        /*
            Method to show a error message TextView.
        */
        binding.txtNoDataAvailable.show()
    }

    private fun showInternetCheckDialog() {
        /*
            Method to show internetCheckDialog.
        */
        internetCheckDialog(
            requireActivity(),
            requireContext(),
            this
        )

    }

    override fun internetCheckDialogPositiveButtonClicked() {
        /*
            Method to control positive button click on InternetCheckDialog.
        */
        checkInternetObserver()
    }

}
package com.circleappsstudio.foggyweather.ui.home

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.*
import com.circleappsstudio.foggyweather.core.permissions.checkLocationPermissions
import com.circleappsstudio.foggyweather.core.time.*
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnInternetCheckDialogButtonClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.showInternetCheckDialog
import com.circleappsstudio.foggyweather.data.model.Locations
import com.circleappsstudio.foggyweather.databinding.FragmentHomeBinding
import com.circleappsstudio.foggyweather.presenter.*
import com.circleappsstudio.foggyweather.ui.home.adapter.AutocompleteAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.Forecast3DaysAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.ForecastByHourAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.hideKeyboard
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), AutocompleteAdapter.OnLocationClickListener,
    OnInternetCheckDialogButtonClickListener {

    private lateinit var binding: FragmentHomeBinding

    private val weatherViewModel by viewModels<WeatherViewModel>()

    private val locationViewModel by viewModels<LocationViewModel>()

    private val internetCheckViewModel by viewModels<InternetCheckViewModel>()

    private val globalPreferencesViewModel by viewModels<GlobalPreferencesViewModel>()

    private val currentDate by lazy {
        formatDate(Calendar.getInstance().time)
    }

    private val currentDateWithMothName by lazy {
        getDateWithMonthName(Calendar.getInstance().time)
    }

    private var cordenades: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.txtCurrentDate.text = currentDateWithMothName

        setupSearchView()

        pullToRefresh()

        requestLocationPermissions()

    }

    private fun getLocationObserver() {

        locationViewModel.fetchLocation(requireContext())
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {
                        cordenades = "${resultEmitted.data[0]},${resultEmitted.data[1]}"
                        checkInternetToGetWeatherInfoObserver()
                    }

                    is Result.Failure -> {

                        requireContext().showToast(
                            requireContext(),
                            "Something went wrong: ${resultEmitted.exception.message}"
                        )

                        hideMainProgressbar()

                    }

                }

            })

    }

    private fun checkInternetToGetWeatherInfoObserver() {

        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {

                            getAllWeatherInfoObserver(
                                location = cordenades,
                                airQuality = false,
                                days = 3,
                                alerts = false,
                                date = currentDate
                            )

                        } else {
                            showNetworkCheckDialog()
                        }

                    }

                    is Result.Failure -> {
                        showNetworkCheckDialog()
                    }

                }

            })

    }

    private fun checkInternetToGetAutocompleteSearchObserver(location: String) {

        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {
                            getAutocompleteResults(location)
                        } else {

                            showNetworkCheckDialog()
                            showMainProgressbar()
                            requireContext().hideKeyboard(requireContext(), requireView())
                            clearSearchView()

                        }

                    }

                    is Result.Failure -> {
                        showNetworkCheckDialog()
                    }

                }

            })

    }

    private fun getAllWeatherInfoObserver(
        location: String,
        airQuality: Boolean,
        days: Int,
        alerts: Boolean,
        date: String
    ) {

        weatherViewModel.getAllWeatherInfo(
            location, airQuality, days, alerts, date
        ).observe(viewLifecycleOwner, Observer { resultEmitted ->
            /*
                t1 = getCurrentWeather.
                t2 = getForecast.
                t3 = getAstronomy.
            */
            when (resultEmitted) {

                is Result.Loading -> {
                    showMainProgressbar()
                }

                is Result.Success -> {

                    // getCurrentWeather:
                    getCurrentWeatherUISetup(
                        name = resultEmitted.data.t1.location.name,
                        region = resultEmitted.data.t1.location.region,
                        temperature = resultEmitted.data.t1.current.temp_c,
                        icon = resultEmitted.data.t1.current.condition.icon,
                        condition = resultEmitted.data.t1.current.condition.text,
                        feelsLike = resultEmitted.data.t1.current.feelslike_c,
                        lastUpdated = resultEmitted.data.t1.current.last_updated
                    )

                    // getForecast:
                    getForecastUISetup(
                        forecastDayList = resultEmitted.data.t2.forecast.forecastday
                    )

                    // getAstronomy:
                    getAstronomyUISetup(
                        sunrise = resultEmitted.data.t3.astronomy.astro.sunrise,
                        sunset = resultEmitted.data.t3.astronomy.astro.sunset,
                        moonrise = resultEmitted.data.t3.astronomy.astro.moonrise,
                        moonset = resultEmitted.data.t3.astronomy.astro.moonset,
                        moonPhase = resultEmitted.data.t3.astronomy.astro.moon_phase,
                        moonIllumination = resultEmitted.data.t3.astronomy.astro.moon_illumination
                    )

                    hideMainProgressbar()

                }

                is Result.Failure -> {
                    // Show try again dialog:
                    requireContext().showToast(
                        requireContext(),
                        "Something went wrong: ${resultEmitted.exception.message}"
                    )

                    binding.mainLayout.hide()
                    binding.progressBar.hide()
                }

            }

        })

    }

    private fun getAutocompleteResults(location: String) {

        weatherViewModel.fetchAutocompleteResults(
            location
        ).observe(
            viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        //showRvAutocompleteProgressbar()
                    }

                    is Result.Success -> {

                        binding.rvAutocomplete.adapter = AutocompleteAdapter(
                            resultEmitted.data,
                            this
                        )

                        //hideRvAutocompleteProgressbar()

                    }

                    is Result.Failure -> {

                        requireContext().showToast(
                            requireContext(),
                            "Something went wrong: ${resultEmitted.exception.message}"
                        )

                        //hideRvAutocompleteProgressbar()

                    }

                }

            })

    }

    private fun getCurrentWeatherUISetup(
        name: String,
        region: String,
        temperature: Float,
        icon: String,
        condition: String,
        feelsLike: Float,
        lastUpdated: String
    ) {

        binding.txtLocation.text = "$name, $region"

        binding.txtTemperature.text = temperature.toString()

        Glide.with(requireContext())
            .load("${AppConstants.BASE_IMAGE_URL}$icon")
            .centerCrop()
            .into(binding.imgIcon)

        binding.txtCondition.text = condition

        binding.txtFeelsLike.text = "${feelsLike}°C"

        val hour = splitDate(lastUpdated)

        val formattedHour = formatHour(
            splitHour(hour, 0),
            splitHour(hour, 1),
            requireContext()
        )

        binding.txtLastUpdate.text = formattedHour

    }

    private fun getForecastUISetup(
        forecastDayList: List<ForecastDay>,
    ) {

        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == 0) {
                binding.txtMaxTemp.text = "${forecastDay.day.maxtemp_c}°C"
                binding.txtMinTemp.text = "${forecastDay.day.mintemp_c}°C"
            }

        }

        val currentHour = getCurrentHourFormatted(requireContext())

        val forecastRecyclerViewPosition = getCurrentForecastCard(
            currentHour, requireContext()
        )

        forecastDayList.forEachIndexed { index, forecastDay ->

            binding.rvForecast.adapter = ForecastByHourAdapter(
                forecastDay.hour,
                forecastRecyclerViewPosition.toString(),
                requireContext()
            )
        }

        binding.rvForecast.scrollToPosition(forecastRecyclerViewPosition)

        binding.rvForecast3Days.adapter = Forecast3DaysAdapter(forecastDayList)

    }

    private fun getAstronomyUISetup(
        sunrise: String,
        sunset: String,
        moonrise: String,
        moonset: String,
        moonPhase: String,
        moonIllumination: String
    ) {

        binding.txtSunrise.text = sunrise
        binding.txtSunset.text = sunset

        binding.txtMoonrise.text = moonrise
        binding.txtMoonset.text = moonset

        binding.txtMoonPhase.text = moonPhase
        binding.txtMoonIllumination.text = moonIllumination

    }

    private fun getSearchedLocationPreference(): String? {
        return globalPreferencesViewModel.getSearchedLocation()
    }

    private fun setSearchedLocationPreference(location: String) {
        return globalPreferencesViewModel.setSearchedLocation(location)
    }

    private fun deleteSearchedLocationPreference() {
        globalPreferencesViewModel.deleteSearchedLocation()
    }

    private fun searchLastLocationIfLocationPermissionIsGranted() {

        if (!getSearchedLocationPreference().isNullOrEmpty()) {
            // There's a location in SharedPreferences.
            cordenades = getSearchedLocationPreference().toString()
            checkInternetToGetWeatherInfoObserver()
        } else {
            // There's not any location in SharedPreferences.
            hideMainLayout()
            hideMainProgressbar()
        }

    }

    private fun showMainProgressbar() {
        binding.progressBar.show()
    }
    private fun hideMainProgressbar() {
        binding.progressBar.hide()
    }

    private fun showRvAutocompleteProgressbar() {
        binding.progressbarRvAutocomplete.show()
    }
    private fun hideRvAutocompleteProgressbar() {
        binding.progressbarRvAutocomplete.hide()
    }

    private fun showSearchRecyclerView() {
        binding.rvAutocomplete.show()
    }

    private fun hideSearchRecyclerView() {
        binding.rvAutocomplete.hide()
    }

    private fun showMainLayout() {
        binding.mainLayout.show()
    }

    private fun hideMainLayout() {
        binding.mainLayout.hide()
    }

    private fun saveSharedPreferenceIfLocationPermissionIsNotGranted(location: String) {
        if (!checkLocationPermissions(requireContext())) {
            setSearchedLocationPreference(location)
        }
    }

    private fun clearSearchView() {
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
        binding.rvAutocomplete.adapter = null // Clear rvAutocomplete adapter.
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {

                text?.let {

                    if (it.length >= 3) {

                        cordenades = it
                        saveSharedPreferenceIfLocationPermissionIsNotGranted(cordenades)
                        checkInternetToGetWeatherInfoObserver()

                        clearSearchView()

                        showMainLayout()

                    } else {

                        requireContext().showToast(
                            requireContext(),
                            "Location not founded!"
                        )

                    }

                }

                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {

                text?.let {

                    if (it.isEmpty()) {
                        hideSearchRecyclerView()
                    } else {
                        showSearchRecyclerView()
                        checkInternetToGetAutocompleteSearchObserver(it)
                    }

                }

                return false
            }

        })

    }

    private fun pullToRefresh() {

        binding.pullToRefresh.setOnRefreshListener {
            // TODO action:
            requestLocationPermissions()
            binding.pullToRefresh.isRefreshing = false
        }

    }

    private fun requestLocationPermissions() {

        binding.progressBar.show()

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

                binding.mainLayout.visibility = View.VISIBLE
                deleteSearchedLocationPreference()
                getLocationObserver()

            } else {

                requireContext().showToast(
                    requireContext(),
                    "Location permission not granted!"
                )

                searchLastLocationIfLocationPermissionIsGranted()

            }

        } else {

            requireContext().showToast(
                requireContext(),
                "Something went wrong"
            )

            hideMainLayout()
            hideMainProgressbar()

        }

    }

    private fun showNetworkCheckDialog() {

        showInternetCheckDialog(
            requireActivity(),
            requireContext(),
            this
        )

    }

    override fun onLocationClick(locations: Locations) {
        binding.searchView.setQuery(
            "${locations.name}, ${locations.region}, ${locations.country}",
            true
        )
    }

    override fun internetCheckDialogPositiveButtonClicked() {
        checkInternetToGetWeatherInfoObserver()
    }

}
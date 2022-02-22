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
import com.circleappsstudio.foggyweather.core.permissions.checkIfLocationPermissionsAreGranted
import com.circleappsstudio.foggyweather.core.time.*
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnInternetCheckDialogButtonClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.internetCheckDialog
import com.circleappsstudio.foggyweather.data.model.Locations
import com.circleappsstudio.foggyweather.databinding.FragmentHomeBinding
import com.circleappsstudio.foggyweather.presenter.*
import com.circleappsstudio.foggyweather.ui.home.adapter.AutocompleteAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.Forecast3DaysAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.ForecastByHourAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.hideKeyboard
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    Forecast3DaysAdapter.OnForecastDayClickListener,
    AutocompleteAdapter.OnLocationClickListener,
    OnInternetCheckDialogButtonClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    private val weatherViewModel by viewModels<WeatherViewModel>()
    private val locationViewModel by viewModels<LocationViewModel>()
    private val internetCheckViewModel by viewModels<InternetCheckViewModel>()
    private val globalPreferencesViewModel by viewModels<GlobalPreferencesViewModel>()
    private val adMobUtilsViewModel by viewModels<AdMobUtilsViewModel>()

    private var coordinates: String = ""

    private val currentDate by lazy { getCurrentDate() }

    private val currentDateWithMothName by lazy {
        getDateWithMonthName(Calendar.getInstance().time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        loadNativeAd()

        searchViewSetup()
        pullToRefreshSetup()
        currentDateTextViewSetup()

        requestLocationPermissionsForSingleTime()

        checkIfIsThereAnyLastLocationSearched()

        getWeatherFromCurrentLocation()

        checkIfLocationPermissionsAreGranted()

    }

    private fun searchViewSetup() {
        /*
            SearchView setup.
        */
        binding.searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->

            if (hasFocus) {
                // If SearchView has focus, show TextView that gets current location.
                showRequestCurrentLocationTextView()
            } else {
                // If SearchView hasn't focus, hide TextView that gets current location.
                hideRequestCurrentLocationTextView()
            }

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {

                text?.let {

                    if (it.length >= 3) {

                        coordinates = it
                        setLastSearchedLocationPreference(it)
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
                        hideAutocompleteRecyclerView()
                    } else {
                        showAutocompleteRecyclerView()
                        checkInternetToGetAutocompleteSearchObserver(it)
                    }

                }

                return false
            }

        })

    }

    private fun pullToRefreshSetup() {
        /*
            Pull to Refresh data setup.
        */
        binding.pullToRefresh.setOnRefreshListener {
            checkIfLocationPermissionsAreGranted()
            binding.pullToRefresh.isRefreshing = false
        }

    }

    private fun checkInternetToGetWeatherInfoObserver() {
        /*
            Method to check if there's internet connection and get weather.
        */
        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {

                            fetchAllWeatherDataObserver(
                                location = coordinates,
                                airQuality = false,
                                days = 3,
                                alerts = false,
                                date = currentDate
                            )

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

    private fun checkInternetToGetAutocompleteSearchObserver(location: String) {
        /*
            Method to check if there's internet connection and get recyclerview autocomplete locations.
        */
        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showRvAutocompleteProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {
                            fetchAutocompleteResults(location)
                        } else {

                            showInternetCheckDialog()
                            clearSearchView()
                            hideRvAutocompleteProgressbar()
                            showMainProgressbar()
                            requireContext().hideKeyboard(requireContext(), requireView())

                        }

                    }

                    is Result.Failure -> {
                        showInternetCheckDialog()
                    }

                }

            })

    }

    private fun fetchLocationObserver() {
        /*
            Method to fetch current location from GPS.
        */
        locationViewModel.fetchLocation(requireContext())
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {

                        if (!checkIfIsThereAnyLastLocationSearched()) {
                            /*
                                If there's not any last location searched, coordinates will be the GPS data.
                                If there's some last location searched, coordinates will be the that location.
                            */
                            coordinates = "${resultEmitted.data[0]},${resultEmitted.data[1]}"
                            setLastSearchedLocationPreference(coordinates)
                        }

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
                    showMainProgressbar()
                }

                is Result.Success -> {
                    /*
                        t1 = getCurrentWeather.
                        t2 = getForecast.
                        t3 = getAstronomy.
                    */

                    // t1 - getCurrentWeather:
                    getCurrentWeatherUISetup(
                        name = resultEmitted.data.t1.location.name,
                        region = resultEmitted.data.t1.location.region,
                        temperature = resultEmitted.data.t1.current.temp_c,
                        icon = resultEmitted.data.t1.current.condition.icon,
                        condition = resultEmitted.data.t1.current.condition.text,
                        feelsLike = resultEmitted.data.t1.current.feelslike_c,
                        lastUpdated = resultEmitted.data.t1.current.last_updated
                    )

                    // t2 - getForecast:
                    getForecastUISetup(
                        forecastDayList = resultEmitted.data.t2.forecast.forecastday
                    )

                    // t3 - getAstronomy:
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

    private fun fetchAutocompleteResults(location: String) {
        /*
            Method to fetch Autocomplete locations data.
        */
        weatherViewModel.fetchAutocompleteResults(
            location
        ).observe(
            viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showRvAutocompleteProgressbar()
                    }

                    is Result.Success -> {

                        binding.rvAutocomplete.adapter = AutocompleteAdapter(
                            resultEmitted.data,
                            this
                        )

                        if (resultEmitted.data.isNotEmpty()) {
                            // If autocomplete results is not empty, hide TextView that gets current location.
                            hideRequestCurrentLocationTextView()
                        } else {
                            // If autocomplete results is empty, show TextView that gets current location.
                            showRequestCurrentLocationTextView()
                        }

                        hideRvAutocompleteProgressbar()

                    }

                    is Result.Failure -> {

                        requireContext().showToast(
                            requireContext(),
                            "Something went wrong: ${resultEmitted.exception.message}"
                        )

                        hideRvAutocompleteProgressbar()

                    }

                }

            })

    }

    private fun getWeatherFromLastLocationSearched() {
        /*
            Method to get weather from last location searched.
        */
        if (checkIfIsThereAnyLastLocationSearched()) {
            // There's a location in SharedPreferences.
            checkInternetToGetWeatherInfoObserver()
        } else {
            // There's not any location in SharedPreferences.
            hideMainLayout()
            hideMainProgressbar()
        }

    }

    private fun checkIfIsThereAnyLastLocationSearched(): Boolean {
        /*
            Method to check if is there any last location searched in SharedPreferences.
        */
        return if (!getLastSearchedLocationPreference().isNullOrEmpty()) {
            coordinates = getLastSearchedLocationPreference().toString()
            true
        } else {
            false
        }
    }

    private fun requestLocationPermissionsForSingleTime() {
        /*
            Method that request location permission for single time.
        */
        val result = globalPreferencesViewModel.wereLocationPermissionsRequestedSingleTime()

        if (!result) {
            // If location permission are not requested for single time yet, requests it.
            requestLocationPermissions()
            setLocationPermissionsRequestedSingleTime()
        }

    }

    private fun requestLocationPermissions() {
        /*
            Method to request location permissions.
        */
        hideMainLayout()

        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), AppConstants.LOCATION_REQUEST_CODE
        )
    }

    private fun getWeatherFromCurrentLocation() {
        /*
            Method to get weather from current location when CurrentLocation TextView is clicked in RecyclerViewAutoComplete.
        */
        hideMainLayout()

        binding.txtRequestCurrentLocation.setOnClickListener {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), AppConstants.LOCATION_REQUEST_CODE
            )
            deleteLastSearchedLocationPreference()
            clearSearchView()
        }

    }

    private fun checkIfLocationPermissionsAreGranted() {
        /*
            Method to check if location permissions are granted.
        */
        if (checkIfLocationPermissionsAreGranted(requireContext())) {

            showMainLayout()
            fetchLocationObserver()

        } else {

            showMainLayout()
            getWeatherFromLastLocationSearched()

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AppConstants.LOCATION_REQUEST_CODE) {

            if (checkIfLocationPermissionsAreGranted(requireContext())) {

                fetchLocationObserver()
                clearSearchView()
                hideRequestCurrentLocationTextView()
                showMainLayout()

            } else {

                requireContext().showToast(
                    requireContext(),
                    "Location permission not granted!"
                )

                showMainLayout()
                getWeatherFromLastLocationSearched()

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

    private fun setLocationPermissionsRequestedSingleTime() {
        /*
            Method to put true when location permission were requested for single time in SharedPreferences.
        */
        globalPreferencesViewModel.setLocationPermissionsRequestedSingleTime()
    }

    private fun getLastSearchedLocationPreference(): String? {
        /*
            Method to get the last location searched from SharedPreferences.
        */
        return globalPreferencesViewModel.getLastSearchedLocation()
    }

    private fun setLastSearchedLocationPreference(location: String) {
        /*
            Method to set a searched location in SharedPreferences.
        */
        return globalPreferencesViewModel.setLastSearchedLocation(location)
    }

    private fun deleteLastSearchedLocationPreference() {
        /*
            Method to delete the searched location from SharedPreferences.
        */
        globalPreferencesViewModel.deleteLastSearchedLocation()
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
        /*
            Method to setup Current Weather UI section.
        */
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
        /*
            Method to setup Forecast UI section.
        */
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

            if (index == 0) {

                binding.rvForecast.adapter = ForecastByHourAdapter(
                    forecastDay.hour,
                    forecastRecyclerViewPosition.toString(),
                    requireContext()
                )

            }

        }

        binding.rvForecast.scrollToPosition(forecastRecyclerViewPosition)

        binding.rvForecast3Days.adapter = Forecast3DaysAdapter(forecastDayList, this)

    }

    private fun getAstronomyUISetup(
        sunrise: String,
        sunset: String,
        moonrise: String,
        moonset: String,
        moonPhase: String,
        moonIllumination: String
    ) {
        /*
            Method to setup Astronomy UI section.
        */
        binding.txtSunrise.text = sunrise
        binding.txtSunset.text = sunset

        binding.txtMoonrise.text = moonrise
        binding.txtMoonset.text = moonset

        binding.txtMoonPhase.text = moonPhase
        binding.txtMoonIllumination.text = moonIllumination

    }

    private fun loadNativeAd() {
        /*
            Method to load a NativeAd.
        */
        adMobUtilsViewModel.loadNativeAd(binding.nativeAd)
    }

    private fun showMainProgressbar() {
        /*
            Method to show MainProgressbar.
        */
        binding.progressBar.show()
    }
    private fun hideMainProgressbar() {
        /*
            Method to hide MainProgressbar.
        */
        binding.progressBar.hide()
    }

    private fun showRvAutocompleteProgressbar() {
        /*
            Method to show RvAutocompleteProgressbar.
        */
        binding.progressbarRvAutocomplete.show()
    }
    private fun hideRvAutocompleteProgressbar() {
        /*
            Method to hide RvAutocompleteProgressbar.
        */
        binding.progressbarRvAutocomplete.hide()
    }

    private fun showAutocompleteRecyclerView() {
        /*
            Method to show AutocompleteRecyclerView.
        */
        binding.rvAutocomplete.show()
    }

    private fun hideAutocompleteRecyclerView() {
        /*
            Method to hide AutocompleteRecyclerView.
        */
        binding.rvAutocomplete.hide()
    }

    private fun showMainLayout() {
        /*
            Method to show MainLayout.
        */
        binding.mainLayout.show()
    }

    private fun hideMainLayout() {
        /*
            Method to hide MainLayout.
        */
        binding.mainLayout.hide()
    }

    private fun showRequestCurrentLocationTextView() {
        /*
            Method to show CurrentLocationTextView.
        */
        binding.txtRequestCurrentLocation.show()
    }

    private fun hideRequestCurrentLocationTextView() {
        /*
            Method to hide CurrentLocationTextView.
        */
        binding.txtRequestCurrentLocation.hide()
    }

    private fun currentDateTextViewSetup() {
        /*
            Method to set current date in UI.
        */
        binding.txtCurrentDate.text = currentDateWithMothName
    }

    private fun goToForecastByDayFragment() {
        /*
            Method to go to ForecastByDayFragment.
        */
        navController.navigate(R.id.fragment_forecast_by_day)
    }

    private fun clearSearchView() {
        /*
            Method to clear SearchView.
        */
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
        binding.rvAutocomplete.adapter = null // Clear rvAutocomplete adapter.
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
        checkInternetToGetWeatherInfoObserver()
    }

    override fun onLocationClick(locations: Locations) {
        /*
            Method to get the searched location in SearchBar.
        */
        binding.searchView.setQuery(
            "${locations.name}, ${locations.region}, ${locations.country}",
            true
        )
    }

    override fun onForecastDayClick(forecastDay: ForecastDay) {
        /*
            Method to set click function in RvForecast RecyclerView.
        */
        Toast.makeText(requireContext(), "${forecastDay.date}", Toast.LENGTH_SHORT).show()
        goToForecastByDayFragment()
    }

}
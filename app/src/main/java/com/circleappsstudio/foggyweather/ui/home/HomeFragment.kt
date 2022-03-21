package com.circleappsstudio.foggyweather.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnInternetCheckDialogClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.internetCheckDialog
import com.circleappsstudio.foggyweather.data.model.Locations
import com.circleappsstudio.foggyweather.databinding.FragmentHomeBinding
import com.circleappsstudio.foggyweather.presenter.*
import com.circleappsstudio.foggyweather.ui.home.adapter.AutocompleteAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.Forecast3DaysAdapter
import com.circleappsstudio.foggyweather.ui.home.adapter.ForecastByHourAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.application.AppConstants.FORECAST_DATE
import com.circleappsstudio.foggyweather.application.AppConstants.FORECAST_LIST
import com.circleappsstudio.foggyweather.application.AppConstants.FORECAST_LIST_POSITION
import com.circleappsstudio.foggyweather.core.permissions.checkIfGPSIsEnabled
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnConfirmationDialogClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.gpsCheckDialog
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.hideKeyboard
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.core.ui.showToast
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    Forecast3DaysAdapter.OnForecastDayClickListener,
    AutocompleteAdapter.OnLocationClickListener,
    OnInternetCheckDialogClickListener,
    OnConfirmationDialogClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    private val weatherViewModel by viewModels<WeatherViewModel>()
    private val locationViewModel by viewModels<LocationViewModel>()
    private val internetCheckViewModel by viewModels<InternetCheckViewModel>()
    private val globalPreferencesViewModel by viewModels<GlobalPreferencesViewModel>()
    private val adMobUtilsViewModel by viewModels<AdMobUtilsViewModel>()

    private var coordinates: String = ""

    private val currentDate by lazy { getCurrentDate() }

    private var forecastDayList: List<ForecastDay> = listOf()
    private var forecast3DaysAdapterPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        loadNativeAd()

        searchViewSetup()
        pullToRefreshSetup()
        currentDateTextViewSetup()
        goToWeatherApiURL()

        requestLocationPermissionsForSingleTime()

        checkIfThereIsAnyLastSearchedLocation()

        getWeatherFromCurrentLocationTextViewClick()

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
                // If SearchView has not focus, hide TextView that gets current location.
                hideRequestCurrentLocationTextView()
            }

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {

                text?.let {

                    if (it.length >= 3) {
                        // If SearchView submitted text has at least 3 characters, search weather data.
                        coordinates = it
                        setLastLocationSearchedPreference(it)
                        checkInternetToGetWeatherDataObserver()

                        clearSearchView()
                        showMainLayout()

                    } else {
                        // If SearchView submitted text has not at least 3 characters, show error message.
                        requireContext().showToast(
                            requireContext(),
                            resources.getString(R.string.location_not_founded)
                        )

                    }

                }
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {

                text?.let {

                    if (it.isEmpty()) {
                        // If SearchView text is empty, hide AutocompleteRecyclerView.
                        hideAutocompleteRecyclerView()
                    } else {
                        // If SearchView text is not empty, show AutocompleteRecyclerView and search locations.
                        showAutocompleteRecyclerView()
                        checkInternetToGetAutocompleteSearchDataObserver(it)
                    }

                }
                return false
            }

        })

    }

    private fun pullToRefreshSetup() {
        /*
            Pull to refresh data setup.
        */
        binding.pullToRefresh.setOnRefreshListener {
            checkIfLocationPermissionsAreGranted()
            binding.pullToRefresh.isRefreshing = false
        }

    }

    private fun checkInternetToGetWeatherDataObserver() {
        /*
            Method to check if there's internet connection and get weather data.
        */
        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showMainProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {

                            loadNativeAd()

                            fetchAllWeatherDataObserver(
                                location = coordinates,
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

    private fun checkInternetToGetAutocompleteSearchDataObserver(location: String) {
        /*
            Method to check if there's internet connection and get autocomplete locations data.
        */
        internetCheckViewModel.checkInternet()
            .observe(viewLifecycleOwner, Observer { resultEmitted ->

                when (resultEmitted) {

                    is Result.Loading -> {
                        showRvAutocompleteProgressbar()
                    }

                    is Result.Success -> {

                        if (resultEmitted.data) {
                            fetchAutocompleteResultsObserver(location)
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

    @ExperimentalCoroutinesApi
    private fun fetchLocationObserver() {
        /*
            Method to fetch current location from GPS.
        */

        showMainProgressbar()

        if (checkIfGPSIsEnabled(requireContext())) {

            locationViewModel.fetchLocation(requireContext())
                .observe(viewLifecycleOwner, Observer { resultEmitted ->

                    when (resultEmitted) {

                        is Result.Loading -> {
                            showMainProgressbar()
                        }

                        is Result.Success -> {

                            if (!checkIfThereIsAnyLastSearchedLocation()) {
                                /*
                                    If there's not any last searched location, coordinates will be the GPS data.
                                    If there's some last searched location, coordinates will be the that location.
                                */
                                coordinates = "${resultEmitted.data[0]},${resultEmitted.data[1]}"
                                setLastLocationSearchedPreference(coordinates)

                            }

                            checkInternetToGetWeatherDataObserver()

                        }

                        is Result.Failure -> {

                            requireContext().showToast(
                                requireContext(),
                                "${resources.getString(R.string.something_went_wrong)}: ${resources.getString(R.string.could_not_get_location)}",
                                Toast.LENGTH_LONG
                            )

                            hideMainProgressbar()
                            hideMainLayout()

                        }

                    }

                })
            
        } else {
            /*
                If GPS is turned off but there is some last searched location, coordinates will be the that location.
                If there is not any last searched location, gpsCheckDialog will be displayed.
            */
            if (checkIfThereIsAnyLastSearchedLocation()) {
                // There is some last searched location.
                checkInternetToGetWeatherDataObserver()
            } else {
                // There is not any last searched location.
                showGPSCheckDialog()
                hideMainProgressbar()
                hideMainLayout()
            }
            
        }

    }

    private fun fetchAllWeatherDataObserver(
        location: String,
        date: String
    ) {
        /*
            Method to fetch all weather data.
        */
        weatherViewModel.fetchAllWeatherData(
            location, date
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
                    //...


                    // t2 - getForecast:
                    forecastDayList = resultEmitted.data.t2.forecast.forecastday
                    getForecastUISetup(
                        forecastDayList = resultEmitted.data.t2.forecast.forecastday,
                        fetchedHour = resultEmitted.data.t1.current.last_updated
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
                    showMainLayout()

                }

                is Result.Failure -> {

                    requireContext().showToast(
                        requireContext(),
                        "${resources.getString(R.string.something_went_wrong)}: ${resultEmitted.exception.message}"
                    )

                    hideMainLayout()
                    hideMainProgressbar()

                }

            }

        })

    }

    private fun fetchAutocompleteResultsObserver(location: String) {
        /*
            Method to fetch autocomplete locations data.
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
                            "${resources.getString(R.string.something_went_wrong)}: ${resultEmitted.exception.message}"
                        )

                        hideRvAutocompleteProgressbar()

                    }

                }

            })

    }

    private fun getWeatherFromLastSearchedLocation() {
        /*
            Method to get weather from last location searched.
        */
        if (checkIfThereIsAnyLastSearchedLocation()) {
            // There's a location in SharedPreferences.
            checkInternetToGetWeatherDataObserver()
        } else {
            // There's not any location in SharedPreferences.
            hideMainLayout()
            hideMainProgressbar()
        }

    }

    private fun checkIfThereIsAnyLastSearchedLocation(): Boolean {
        /*
            Method to check if there is any last searched location in SharedPreferences.
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
            Method that requests location permission for single time.
        */
        val result = didLocationPermissionsAreRequestedSingleTimePreference()

        if (!result) {
            // If location permission are not requested for single time yet, request them.
            requestLocationPermissions()
            setLocationPermissionsRequestedSingleTimePreference()
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

    private fun getWeatherFromCurrentLocationTextViewClick() {
        /*
            Method to get weather from current location when CurrentLocation TextView is clicked in RecyclerViewAutoComplete.
        */
        binding.txtRequestCurrentLocation.setOnClickListener {

            hideMainLayout()

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

    @ExperimentalCoroutinesApi
    private fun checkIfLocationPermissionsAreGranted() {
        /*
            Method to check if location permission (GPS) are granted.
        */
        if (
            checkIfLocationPermissionsAreGranted(requireContext())
        ) {
            /*
                Location permissions are requested already and GPS is enabled.
            */
            fetchLocationObserver()
        } else {
            /*
                Location permissions are not requested and GPS is disabled.
            */
            getWeatherFromLastSearchedLocation()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AppConstants.LOCATION_REQUEST_CODE) {

            if (
                checkIfLocationPermissionsAreGranted(requireContext())
            ) {

                if (checkIfGPSIsEnabled(requireContext())) {

                    fetchLocationObserver()
                    clearSearchView()
                    hideRequestCurrentLocationTextView()
                    showMainLayout()

                } else {
                    showGPSCheckDialog()
                    showRequestCurrentLocationTextView()
                    hideMainLayout()
                    hideMainProgressbar()
                }

            } else {

                requireContext().showToast(
                    requireContext(),
                    resources.getString(R.string.location_permissions_not_granted)
                )

                showMainLayout()
                getWeatherFromLastSearchedLocation()

            }

        } else {

            requireContext().showToast(
                requireContext(),
                resources.getString(R.string.something_went_wrong)
            )

            hideMainLayout()
            hideMainProgressbar()

        }

    }

    private fun didLocationPermissionsAreRequestedSingleTimePreference(): Boolean {
        /*
            Method to check if location permission were requested for single time.
        */
        return globalPreferencesViewModel.didLocationPermissionsAreRequestedSingleTime()
    }

    private fun setLocationPermissionsRequestedSingleTimePreference() {
        /*
            Method to put true when location permission are requested for single time.
        */
        globalPreferencesViewModel.setLocationPermissionsRequestedSingleTime()
    }

    private fun getLastSearchedLocationPreference(): String? {
        /*
            Method to get the last searched location.
        */
        return globalPreferencesViewModel.getLastSearchedLocation()
    }

    private fun setLastLocationSearchedPreference(location: String) {
        /*
            Method to set the last searched location.
        */
        return globalPreferencesViewModel.setLastSearchedLocation(location)
    }

    private fun deleteLastSearchedLocationPreference() {
        /*
            Method to delete the last searched location.
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
            Method to setup current weather UI section.
        */
        Glide.with(requireContext())
            .load("${AppConstants.BASE_IMAGE_URL}$icon")
            .centerCrop()
            .into(binding.imgIcon)

        binding.txtTemperature.text = temperature.toString()

        binding.txtLocation.text = "$name, $region"

        binding.txtCondition.text = condition

        binding.txtFeelsLike.text = "${feelsLike}${resources.getString(R.string.celsius)}"

        val hour = splitDate(lastUpdated, 1)

        val formattedHour = getAnyHourFormatted(
            splitHour(hour, 0),
            splitHour(hour, 1),
            requireContext()
        )

        binding.txtLastUpdate.text = formattedHour

    }

    private fun getForecastUISetup(
        forecastDayList: List<ForecastDay>,
        fetchedHour: String
    ) {
        /*
            Method to setup forecast UI section.
        */
        forecastDayList.forEachIndexed { index, forecastDay ->

            if (index == 0) {

                binding.txtMaxTemp.text = "${forecastDay.day.maxtemp_c}${resources.getString(R.string.celsius)}"
                binding.txtMinTemp.text = "${forecastDay.day.mintemp_c}${resources.getString(R.string.celsius)}"

                val hour = splitHour(splitDate(fetchedHour, 1), 0)
                val minute = splitHour(splitDate(fetchedHour, 1), 1)
                val formattedHour = getAnyHourFormatted(hour, minute, requireContext())

                val forecastRecyclerViewPosition = getCurrentForecastCard(
                    formattedHour, requireContext()
                )

                binding.rvForecast.adapter = ForecastByHourAdapter(
                    forecastDay.hour,
                    forecastRecyclerViewPosition.toString(),
                    requireContext()
                )

                binding.rvForecast.scrollToPosition(forecastRecyclerViewPosition)

            }

        }

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
            Method to setup astronomy UI section.
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
        binding.txtCurrentDate.text = getDateWithMonthName(
            Calendar.getInstance().time
        )
    }

    private fun goToLocationSettings() {
        /*
            Method to navigate to Android's location settings.
        */
        requireActivity().startActivity(
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        )
    }
    
    private fun goToWeatherApiURL() {
        /*
            Method to navigate to weatherapi.com web page.
        */
        binding.txtWeatherApiLink.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.weather_api_url)
                )
            )

            requireContext().startActivity(intent)

        }
    }

    private fun goToForecastByDayFragment(date: String) {
        /*
            Method to go to ForecastByDayFragment.
        */
        navController.navigate(
            R.id.fragment_forecast_by_day, bundleOf(
                FORECAST_LIST to forecastDayList,
                FORECAST_LIST_POSITION to forecast3DaysAdapterPosition,
                FORECAST_DATE to date
            )
        )

    }

    private fun requestFocusSearchView() {
        /*
            Method to request focus on SearchView.
        */
        binding.searchView.requestFocus()
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

    private fun showGPSCheckDialog() {
        /*
            Method to show internetCheckDialog.
        */
        gpsCheckDialog(
            requireActivity(),
            requireContext(),
            this
        )

    }

    override fun internetCheckDialogPositiveButtonClicked() {
        /*
            Method to control positive button click on InternetCheckDialog.
        */
        checkInternetToGetWeatherDataObserver()
    }

    override fun confirmationDialogPositiveButtonClicked() {
        /*
            Method to control positive button click on GPSCheckDialog.
        */
        goToLocationSettings()
        requestFocusSearchView()
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

    override fun onForecastDayClick(forecastDay: ForecastDay, position: Int) {
        /*
            Method to set click function in RvForecast3Days RecyclerView.
        */
        forecast3DaysAdapterPosition = position
        goToForecastByDayFragment(forecastDay.date)
    }

}
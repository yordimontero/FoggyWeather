package com.circleappsstudio.foggyweather.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.AppConstants.CELSIUS
import com.circleappsstudio.foggyweather.application.AppConstants.FAHRENHEIT
import com.circleappsstudio.foggyweather.core.ui.*
import com.circleappsstudio.foggyweather.databinding.FragmentSettingsBinding
import com.circleappsstudio.foggyweather.presenter.GlobalPreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    private val globalPreferencesViewModel by viewModels<GlobalPreferencesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        checkSelectedTemperatureUnit()
        temperatureUnitButtonsListener()

        checkSelectedTheme()
        themeButtonsListener()

    }

    private fun checkSelectedTemperatureUnit() {
        /*
            Method to check which temperature is selected.
        */

        // Check which temperature unit is selected.
        when (getTemperatureUnitPreference()) {

            CELSIUS -> {
                // Selected temperature unit is celsius.
                binding.btnCelsius.isChecked = true
            }

            FAHRENHEIT -> {
                // Selected temperature unit is fahrenheit.
                binding.btnFahrenheit.isChecked = true
            }

        }

    }

    private fun temperatureUnitButtonsListener() {
        /*
            Method to listen which temperature unit Button is pressed.
        */
        binding.btnCelsius.setOnClickListener {

            if (binding.btnFahrenheit.isChecked) {
                binding.btnFahrenheit.isChecked = false
            }

            // Set temperature unit SharedPreference to celsius.
            setTemperatureUnitPreference(CELSIUS)

        }

        binding.btnFahrenheit.setOnClickListener {

            if (binding.btnCelsius.isChecked) {
                binding.btnCelsius.isChecked = false
            }

            // Set temperature unit SharedPreference to fahrenheit.
            setTemperatureUnitPreference(FAHRENHEIT)

        }

    }

    private fun checkSelectedTheme() {
        /*
            Method to check which UI mode is enabled.
        */

        /*
            Check if default mode UI (AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
            or automatic mode UI (AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) is enabled.
        */
        if (checkIfDefaultModeIsEnabled() || checkIfAutomaticModeIsEnabled()) {
            // Default or automatic mode UI mode is enabled.
            binding.btnAutomaticMode.isChecked = true

        } else {
            /*
                Default or automatic mode UI mode is disabled.
                Check which UI mode is enabled.
            */
            when (checkCurrentUIMode(requireContext())) {

                "Light Mode" -> {
                    // Light mode is enabled.
                    binding.btnLightMode.isChecked = true
                }

                "Dark Mode" -> {
                    // Dark mode is enabled.
                    binding.btnDarkMode.isChecked = true
                }

            }

        }

    }

    private fun themeButtonsListener() {
        /*
            Method to listen which theme Button is pressed.
        */
        binding.btnLightMode.setOnClickListener {

            if (binding.btnDarkMode.isChecked) {
                binding.btnDarkMode.isChecked = false
            }

            if (binding.btnAutomaticMode.isChecked) {
                binding.btnAutomaticMode.isChecked = false
            }

            // Set light mode.
            setLightMode()

        }

        binding.btnDarkMode.setOnClickListener {

            if (binding.btnLightMode.isChecked) {
                binding.btnLightMode.isChecked = false
            }

            if (binding.btnAutomaticMode.isChecked) {
                binding.btnAutomaticMode.isChecked = false
            }

            // Set dark mode.
            setDarkMode()

        }

        binding.btnAutomaticMode.setOnClickListener {

            if (binding.btnLightMode.isChecked) {
                binding.btnLightMode.isChecked = false
            }

            if (binding.btnDarkMode.isChecked) {
                binding.btnDarkMode.isChecked = false
            }

            // Set automatic mode.
            setAutomaticMode()

        }

    }

    private fun getTemperatureUnitPreference(): String? {
        /*
            Method to get selected temperature unit.
        */
        return globalPreferencesViewModel.getTemperatureUnit()
    }

    private fun setTemperatureUnitPreference(temperatureUnit: String) {
        /*
            Method to set selected temperature unit.
        */
        globalPreferencesViewModel.setTemperatureUnit(temperatureUnit)
    }

}
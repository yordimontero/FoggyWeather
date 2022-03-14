package com.circleappsstudio.foggyweather.application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnConfirmationDialogClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.appRateDialog
import com.circleappsstudio.foggyweather.core.ui.hide
import com.circleappsstudio.foggyweather.core.ui.show
import com.circleappsstudio.foggyweather.databinding.ActivityMainBinding
import com.circleappsstudio.foggyweather.presenter.AdMobUtilsViewModel
import com.circleappsstudio.foggyweather.presenter.AppRateUtilsViewModel
import com.circleappsstudio.foggyweather.presenter.GlobalPreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    OnConfirmationDialogClickListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val adMobUtilsViewModel by viewModels<AdMobUtilsViewModel>()
    private val appRateUtilsViewModel by viewModels<AppRateUtilsViewModel>()
    private val globalPreferencesViewModel by viewModels<GlobalPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_home, R.id.fragment_more
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.btnArrowBack.setOnClickListener(this)

        setBottomNavigationVisibility()

        initAdMob()

        initAppRate()

    }

    override fun onClick(view: View?) {

        if (binding.btnArrowBack == view) {
            navController.navigateUp()
        }

    }

    private fun setBottomNavigationVisibility() {
        /*
            Method to change BottomNavigationView visibility.
        */
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {

                R.id.fragment_home -> {
                    showBottomNavigation()
                    hideArrowBack()
                }

                R.id.fragment_forecast_by_day -> {
                    hideBottomNavigation()
                    showArrowBack()
                }

                else -> {
                    showBottomNavigation()
                    showArrowBack()
                }

            }

        }

    }

    private fun initAdMob() {
        /*
            Method to initialize AdMob.
        */
        adMobUtilsViewModel.initAdMob()
    }

    private fun initAppRate() {
        /*
            Method to initialize AppRate.
        */
        if (appRateUtilsViewModel.initAppRate() && !didRateAppDialogIsLaunched()) {
            showRateAppDialog()
        }

    }

    private fun didRateAppDialogIsLaunched(): Boolean {
        /*
            Method to check if rate app dialog was already launched or not.
        */
        return globalPreferencesViewModel.didRateAppDialogIsLaunched()
    }

    private fun setRateAppDialogLaunched() {
        /*
            Method to put true when rate app dialog is already launched.
        */
        globalPreferencesViewModel.setRateAppDialogLaunched()
    }

    private fun goToPlayStore() {
        /*
            Method to go to Google Play Store to user can rate the app.
        */
        val packageName: String = packageName
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        )
        startActivity(intent)
    }

    private fun showBottomNavigation() {
        /*
            Method to show BottomNavigation.
        */
        binding.navView.show()
    }

    private fun hideBottomNavigation() {
        /*
            Method to hide BottomNavigation.
        */
        binding.navView.hide()
    }

    private fun showArrowBack() {
        /*
            Method to show ArrowBack.
        */
        binding.btnArrowBack.show()
    }

    private fun hideArrowBack() {
        /*
            Method to hide ArrowBack.
        */
        binding.btnArrowBack.hide()
    }

    private fun showRateAppDialog() {
        /*
            Method to show rateAppDialog.
        */
        appRateDialog(
            this,
            this,
            this
        )

        setRateAppDialogLaunched()
    }

    override fun confirmationDialogPositiveButtonClicked() {
        /*
            Method that controls positive button of showAppRateDialog.
        */
        goToPlayStore()
    }

}
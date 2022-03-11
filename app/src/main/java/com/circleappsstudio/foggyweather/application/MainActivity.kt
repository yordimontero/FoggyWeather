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
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnConfirmationDialogButtonClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.rateAppDialog
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
    OnConfirmationDialogButtonClickListener {

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

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnArrowBack.setOnClickListener(this)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_home, R.id.fragment_more
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

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

        if (appRateUtilsViewModel.initAppRate() && !globalPreferencesViewModel.isRateAppDialogLaunched()) {
            showRateAppDialog()
        }

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

    private fun showRateAppDialog() {
        /*
            Method to show rateAppDialog.
        */
        rateAppDialog(
            this,
            this,
            this
        )

        globalPreferencesViewModel.setRateAppDialogLaunched()
    }

    override fun confirmationDialogPositiveButtonClicked() {
        /*
            Method that controls positive button of showAppRateDialog.
        */
        goToPlayStore()
    }


    private fun showBottomNavigation() {
        binding.navView.show()
    }

    private fun hideBottomNavigation() {
        binding.navView.hide()
    }

    private fun showArrowBack() {
        binding.btnArrowBack.show()
    }

    private fun hideArrowBack() {
        binding.btnArrowBack.hide()
    }

}
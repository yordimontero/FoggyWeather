package com.circleappsstudio.foggyweather.application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.core.ui.customdialogs.OnConfirmationDialogButtonClickListener
import com.circleappsstudio.foggyweather.core.ui.customdialogs.rateAppDialog
import com.circleappsstudio.foggyweather.databinding.ActivityMainBinding
import com.circleappsstudio.foggyweather.presenter.AdMobUtilsViewModel
import com.circleappsstudio.foggyweather.presenter.AppRateUtilsViewModel
import com.circleappsstudio.foggyweather.presenter.GlobalPreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
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

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_home, R.id.fragment_more
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        initAdMob()
        initAppRate()

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

}
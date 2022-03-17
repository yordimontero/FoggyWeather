package com.circleappsstudio.foggyweather.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToMainScreen()
    }

    private fun goToMainScreen() {
        /*
            Method to navigate to MainScreen.
        */
        Handler(
            Looper.getMainLooper()
        ).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }, 2000)

    }

}
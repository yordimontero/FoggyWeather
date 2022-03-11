package com.circleappsstudio.foggyweather.presenter

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.application.AdMobUtils
import com.circleappsstudio.foggyweather.core.Result
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AdMobUtilsViewModel @Inject constructor(
    private val adMobUtils: AdMobUtils
): ViewModel() {

    fun initAdMob() {
        /*
            Method to initialize AdMob.
        */
        adMobUtils.initAdMob()
    }

    fun loadNativeAd(templateView: TemplateView) {
        /*
            Method to load a NativeAd.
        */
        adMobUtils.loadNativeAd(templateView)
    }

    fun loadBannerAdMob(): AdRequest = adMobUtils.loadBannerAdMob()

    //fun showInterstitialAdMob(activity: Activity) = adMobUtils.showInterstitialAdMob(activity)

    @ExperimentalCoroutinesApi
    fun showInterstitialAdMob() = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {

        try {

            emit(
                Result.Loading()
            )

            emit(
                Result.Success(
                    adMobUtils.showInterstitialAdMob()
                )
            )

        } catch (e: Exception) {

            emit(
                Result.Failure(e)
            )

        }

    }

}
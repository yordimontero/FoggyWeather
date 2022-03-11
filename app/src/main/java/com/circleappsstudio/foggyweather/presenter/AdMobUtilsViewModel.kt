package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.application.AdMobUtils
import com.circleappsstudio.foggyweather.core.Result
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
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

    fun buildAdRequest(): AdRequest {
        /*
            Method to build an AdRequest.
        */
        return adMobUtils.buildAdRequest()
    }

    @ExperimentalCoroutinesApi
    fun showInterstitialAd() = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {
        /*
            Method to load an InterstitialAd.
        */
        try {

            emit(
                Result.Loading()
            )

            emit(
                Result.Success(
                    adMobUtils.showInterstitialAd()
                )
            )

        } catch (e: Exception) {

            emit(
                Result.Failure(e)
            )

        }

    }

    fun showBannerAd(banner: AdView) {
        /*
            Method to show an BannerAd.
        */
        adMobUtils.showBannerAd(banner)
    }

}
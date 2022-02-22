package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.AdMobUtils
import com.google.android.ads.nativetemplates.TemplateView
import dagger.hilt.android.lifecycle.HiltViewModel
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

}
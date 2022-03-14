package com.circleappsstudio.foggyweather.application.admob

import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface AdMobUtils {

    fun initAdMob()

    fun loadNativeAd(templateView: TemplateView)

    fun buildAdRequest(): AdRequest

    @ExperimentalCoroutinesApi
    suspend fun showInterstitialAd(): InterstitialAd?

    fun showBannerAd(banner: AdView)

}
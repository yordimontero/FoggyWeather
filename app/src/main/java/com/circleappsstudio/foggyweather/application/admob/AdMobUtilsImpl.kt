package com.circleappsstudio.foggyweather.application.admob

import android.content.Context
import com.google.android.gms.ads.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class AdMobUtilsImpl @Inject constructor(
    @ApplicationContext val context: Context
): AdMobUtils {

    override fun initAdMob() {
        /*
            Method to initialize AdMob.
        */
        MobileAds.initialize(context)
    }

    override fun loadNativeAd(templateView: TemplateView) {
        /*
            Method to load a NativeAd.
        */
        // ID NativeAd: ca-app-pub-2174482267964625/5821815502
        // ID Test NativeAd: ca-app-pub-3940256099942544/2247696110
        val adLoader: AdLoader = AdLoader.Builder(
            context,
            "ca-app-pub-3940256099942544/2247696110"
        ).forNativeAd {
            val styles = NativeTemplateStyle.Builder().build()
            val template: TemplateView = templateView
            template.setStyles(styles)
            template.setNativeAd(it)
        }.build()

        adLoader.loadAd(
            buildAdRequest()
        )

    }

    override fun buildAdRequest(): AdRequest {
        /*
            Method to build an AdRequest.
        */
        return AdRequest.Builder().build()
    }

    @ExperimentalCoroutinesApi
    override suspend fun showInterstitialAd(): InterstitialAd? = suspendCancellableCoroutine { cont ->
        /*
            Method to load an InterstitialAd.
            It returns an InterstitialAd, that is used to show it in a View (Activity or Fragment).
        */
        // ID InterstitialAd: ca-app-pub-2174482267964625/9413972937
        // ID Test InterstitialAd: ca-app-pub-3940256099942544/1033173712

        val loadCallback = object : InterstitialAdLoadCallback() {

            override fun onAdLoaded(ad: InterstitialAd) {
                super.onAdLoaded(ad)
                cont.resume(ad, null)
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                cont.resume(null, null)
            }

        }

        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            buildAdRequest(),
            loadCallback
        )

    }

    override fun showBannerAd(banner: AdView) {
        /*
            Method to show an BannerAd.
        */
        // ID Banner: ca-app-pub-2174482267964625/1458110228
        // ID Test Banner: ca-app-pub-3940256099942544/6300978111
        banner.loadAd(
            buildAdRequest()
        )
    }

}
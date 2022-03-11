package com.circleappsstudio.foggyweather.application

import android.app.Activity
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

class AdMobUtils @Inject constructor(@ApplicationContext val context: Context) {

    private var mInterstitialAd: InterstitialAd? = null

    fun initAdMob() {
        /*
            Method to initialize AdMob.
        */
        MobileAds.initialize(context)
    }

    fun loadNativeAd(templateView: TemplateView) {
        /*
            Method to load a NativeAd.
        */
        val adLoader: AdLoader = AdLoader.Builder(
            context,
            "ca-app-pub-3940256099942544/2247696110"
        ).forNativeAd {
                val styles = NativeTemplateStyle.Builder().build()
                val template: TemplateView = templateView
                template.setStyles(styles)
                template.setNativeAd(it)
            }.build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    fun loadBannerAdMob() : AdRequest {
        /*
            Método para cargar el anuncio Banner "BannerMain".
            Retorna un AdRequest, el cual se utiliza para setearselo al XML del banner.
        */
        // ID Banner:
        // ID Test Banner: ca-app-pub-3940256099942544/6300978111
        return AdRequest.Builder().build()

    }

    /*fun showInterstitialAdMob(activity: Activity) {
        /*
            Método para cargar el anuncio Intersticial "Interstitial_Main".
        */
        // ID Interstitial:
        // ID Test Interstitial: ca-app-pub-3940256099942544/1033173712

        // Create Callback:


        /*InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.show(activity)
                }
            })*/

    }*/

    /*@ExperimentalCoroutinesApi
    suspend fun showInterstitialAdMob(activity: Activity): Boolean = suspendCancellableCoroutine { cont ->

        val adRequest = AdRequest.Builder().build()

        val loadCallback = object : InterstitialAdLoadCallback() {

            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                cont.resume(true, null)
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                cont.resume(false, null)
            }

        }

        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            loadCallback
        )

    }*/

    @ExperimentalCoroutinesApi
    suspend fun showInterstitialAdMob(): InterstitialAd? = suspendCancellableCoroutine { cont ->

        val adRequest = AdRequest.Builder().build()

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
            adRequest,
            loadCallback
        )

    }

}
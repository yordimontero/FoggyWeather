package com.circleappsstudio.foggyweather.application

import android.content.Context
import com.google.android.gms.ads.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.ads.nativetemplates.NativeTemplateStyle

class AdMobUtils @Inject constructor(@ApplicationContext val context: Context) {

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

}
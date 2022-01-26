package com.circleappsstudio.foggyweather.application.injection

import android.content.Context
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.data.webservice.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherRetrofitInstance() = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).build()

    @Singleton
    @Provides
    fun provideWeatherWebService(retrofit: Retrofit) = retrofit.create(
        WebService::class.java
    )

    @Singleton
    @Provides
    fun provideGlobalPreferences(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(
        AppConstants.GLOBAL_PREFERENCE, Context.MODE_PRIVATE
    )

}
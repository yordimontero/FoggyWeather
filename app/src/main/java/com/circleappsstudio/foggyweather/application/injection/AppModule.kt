package com.circleappsstudio.foggyweather.application.injection

import android.content.Context
import android.content.SharedPreferences
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.application.GlobalPreferences
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

/*
    Dagger Hilt Dependency Module.
    @InstallIn() is the scope that this @Module lives.
*/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
        Creating dependency for Retrofit instance.
    */
    @Singleton
    @Provides
    fun provideWeatherRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).build()

    /*
        Creating dependency for WebService instance.
    */
    @Singleton
    @Provides
    fun provideWeatherWebService(
        retrofit: Retrofit
    ): WebService = retrofit.create(
        WebService::class.java
    )

    /*
        Creating dependency for SharedPreferences instance.
    */
    @Singleton
    @Provides
    fun provideGlobalPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        AppConstants.GLOBAL_PREFERENCE, Context.MODE_PRIVATE
    )

}
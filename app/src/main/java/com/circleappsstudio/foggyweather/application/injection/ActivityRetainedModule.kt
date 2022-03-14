package com.circleappsstudio.foggyweather.application.injection

import com.circleappsstudio.foggyweather.application.admob.AdMobUtils
import com.circleappsstudio.foggyweather.application.admob.AdMobUtilsImpl
import com.circleappsstudio.foggyweather.application.apprate.AppRateUtils
import com.circleappsstudio.foggyweather.application.apprate.AppRateUtilsImpl
import com.circleappsstudio.foggyweather.application.location.LocationUtils
import com.circleappsstudio.foggyweather.application.location.LocationUtilsImpl
import com.circleappsstudio.foggyweather.application.preferences.GlobalPreferences
import com.circleappsstudio.foggyweather.application.preferences.GlobalPreferencesImpl
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSourceImpl
import com.circleappsstudio.foggyweather.repository.location.LocationRepository
import com.circleappsstudio.foggyweather.repository.location.LocationRepositoryImpl
import com.circleappsstudio.foggyweather.repository.weather.WeatherRepository
import com.circleappsstudio.foggyweather.repository.weather.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/*
    Dagger Hilt Dependency Module.
    @InstallIn(ActivityRetainedComponent::class) is injected for ViewModels.
*/
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    /*
        @Binds binds (match) Interfaces with Implementations and Provides the Interfaces.
    */

    @Binds
    abstract fun bindLocationRepositoryImpl(
        /*
            Bind LocationRepositoryImpl with their Interface (LocationRepository) and Provide this Interface.
        */
        repository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun bindWeatherRepositoryImpl(
        /*
            Bind WeatherRepositoryImpl with their Interface (WeatherRepository) and Provide this Interface.
        */
        repository: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun bindWeatherRemoteDataSourceImpl(
        /*
            Bind WeatherRemoteDataSourceImpl with their Interface (WeatherRemoteDataSource) and Provide this Interface.
        */
        dataSource: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource

    @Binds
    abstract fun bindAdMobUtilsImpl(
        /*
            Bind AdMobUtilsImpl with their Interface (AdMobUtils) and Provide this Interface.
        */
        adMobUtils: AdMobUtilsImpl
    ): AdMobUtils

    @Binds
    abstract fun bindAppRateUtilsImpl(
        /*
            Bind AppRateUtilsImpl with their Interface (AppRateUtils) and Provide this Interface.
        */
        appRateUtils: AppRateUtilsImpl
    ): AppRateUtils

    @Binds
    abstract fun bindLocationUtilsImpl(
        /*
            Bind LocationUtilsImpl with their Interface (LocationUtils) and Provide this Interface.
        */
        locationUtils: LocationUtilsImpl
    ): LocationUtils

    @Binds
    abstract fun bindGlobalPreferencesImpl(
        /*
            Bind GlobalPreferencesImpl with their Interface (GlobalPreferences) and Provide this Interface.
        */
        globalPreferences: GlobalPreferencesImpl
    ): GlobalPreferences

}
package com.circleappsstudio.foggyweather.application.injection

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

}
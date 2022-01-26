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

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    abstract fun bindLocationRepositoryImpl(
        repository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun bindWeatherRepositoryImpl(
        useCase: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun bindWeatherRemoteDataSourceImpl(
        dataSource: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource

}
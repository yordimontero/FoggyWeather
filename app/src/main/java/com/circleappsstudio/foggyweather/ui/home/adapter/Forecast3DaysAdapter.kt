package com.circleappsstudio.foggyweather.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.application.AppConstants.CELSIUS
import com.circleappsstudio.foggyweather.application.AppConstants.FAHRENHEIT
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.core.time.convertStringToDate
import com.circleappsstudio.foggyweather.core.time.getDateWithMonthName
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.Forecast3DaysItemViewBinding

class Forecast3DaysAdapter(
    private val forecast3DaysList: List<ForecastDay>,
    private val temperatureUnit: String?,
    private val itemClickListener: OnForecastDayClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnForecastDayClickListener {
        /*
            Interface to set click function in each item from RecyclerView.
        */
        fun onForecastDayClick(forecastDay: ForecastDay, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*
            onCreateViewHolder returns class that binds each RecyclerView element.
            It inflates the layout that will display the data.
        */
        val itemBinding = Forecast3DaysItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = Forecast3DaysViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            // Click on RecyclerView item with their position.

            val position = holder.adapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener

            itemClickListener.onForecastDayClick(forecast3DaysList[position], position)

        }

        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        /*
            Each RecyclerView element binds.
        */
        when (holder) {
            is Forecast3DaysViewHolder -> {
                holder.bind(forecast3DaysList[position])
            }
        }

    }

    override fun getItemCount(): Int = forecast3DaysList.size

    private inner class Forecast3DaysViewHolder(
        val binding: Forecast3DaysItemViewBinding,
        val context: Context
    ): BaseViewHolder<ForecastDay>(binding.root) {

        override fun bind(item: ForecastDay) {
            /*
                bind(...) method creates each element to "draw" in RecyclerView.
            */
            convertStringToDate(item.date)?.let {
                binding.txtDate.text = getDateWithMonthName(it)
            }

            binding.txtCondition.text = item.day.condition.text

            Glide.with(context)
                .load("${AppConstants.BASE_IMAGE_URL}${item.day.condition.icon}")
                .centerCrop()
                .into(binding.imgIcon)

            when (temperatureUnit) {

                CELSIUS -> {

                    binding.txtMaxTemp.text = item.day.maxtemp_c.toString()
                    binding.txtTemperatureUnitMax.text = context.resources.getString(R.string.celsius)

                    binding.txtMinTemp.text = item.day.mintemp_c.toString()
                    binding.txtTemperatureUnitMin.text = context.resources.getString(R.string.celsius)

                }

                FAHRENHEIT -> {

                    binding.txtMaxTemp.text = item.day.maxtemp_f.toString()
                    binding.txtTemperatureUnitMax.text = context.resources.getString(R.string.fahrenheit)

                    binding.txtMinTemp.text = item.day.mintemp_f.toString()
                    binding.txtTemperatureUnitMin.text = context.resources.getString(R.string.fahrenheit)

                }

            }

        }

    }

}
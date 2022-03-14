package com.circleappsstudio.foggyweather.ui.forecastbyday.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.core.time.formatHour
import com.circleappsstudio.foggyweather.core.time.splitDate
import com.circleappsstudio.foggyweather.core.time.splitHour
import com.circleappsstudio.foggyweather.data.model.Hour
import com.circleappsstudio.foggyweather.databinding.ForecastByDayItemViewBinding

class ForecastByDayAdapter(
    private val forecastByHourList: List<Hour>
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*
            onCreateViewHolder returns class that binds each RecyclerView element.
            It inflates the layout that will display the data.
        */

        val itemBinding = ForecastByDayItemViewBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ForecastByDayViewHolder(itemBinding, parent.context)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        /*
            Each RecyclerView element binds.
        */
        when (holder) {

            is ForecastByDayViewHolder -> {
                holder.bind(forecastByHourList[position])
            }

        }

    }

    override fun getItemCount(): Int = forecastByHourList.size

    private inner class ForecastByDayViewHolder(
        val binding: ForecastByDayItemViewBinding,
        val context: Context
    ): BaseViewHolder<Hour>(binding.root) {

        override fun bind(item: Hour) {
            /*
                bind(...) method creates each element to "draw" in RecyclerView.
            */
            val hour = splitDate(item.time)

            val formattedHour = formatHour(
                splitHour(hour, 0),
                splitHour(hour, 1),
                context
            )

            binding.txtHour.text = formattedHour

            Glide.with(context)
                .load("${AppConstants.BASE_IMAGE_URL}${item.condition.icon}")
                .centerCrop()
                .into(binding.imgIcon)

            binding.txtCondition.text = item.condition.text

            binding.txtTemperature.text = item.temp_c

            binding.txtFeelsLike.text = item.feelslike_c

            binding.txtChanceOfRain.text = item.chance_of_rain.toString()

        }

    }

}
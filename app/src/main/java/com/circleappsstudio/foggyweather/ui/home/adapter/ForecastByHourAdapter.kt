package com.circleappsstudio.foggyweather.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.core.time.getAnyHourFormatted
import com.circleappsstudio.foggyweather.core.time.splitDate
import com.circleappsstudio.foggyweather.core.time.splitHour
import com.circleappsstudio.foggyweather.core.ui.changeSelectedForecastCardViewColor
import com.circleappsstudio.foggyweather.core.ui.changeUnselectedForecastCardViewColor
import com.circleappsstudio.foggyweather.data.model.Hour
import com.circleappsstudio.foggyweather.databinding.ForecastItemViewBinding

class ForecastByHourAdapter(
    private val forecastByHourList: List<Hour>,
    private var currentHour: String,
    private val context: Context
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*
            onCreateViewHolder returns class that binds each RecyclerView element.
            It inflates the layout that will display the data.
        */
        val itemBinding = ForecastItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ForecastViewHolder(itemBinding, parent.context)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        /*
            Each RecyclerView element binds.
        */
        when (holder) {

            is ForecastViewHolder -> {

                holder.bind(forecastByHourList[position])

                val hour = splitDate(forecastByHourList[position].time, 1)
                val splittedHour = splitHour(hour, 0).toInt()

                if (currentHour.toInt() == splittedHour) {

                    changeSelectedForecastCardViewColor(
                        context = context,
                        primaryCardView = holder.binding.primaryCardView,
                        secondaryCardView = holder.binding.secondaryCardView,
                        hour = holder.binding.txtHour,
                        temperature = holder.binding.txtTemperature,
                        grades = holder.binding.txtGrades
                    )

                } else {

                    changeUnselectedForecastCardViewColor(
                        context = context,
                        primaryCardView = holder.binding.primaryCardView,
                        secondaryCardView = holder.binding.secondaryCardView,
                        hour = holder.binding.txtHour,
                        temperature = holder.binding.txtTemperature,
                        grades = holder.binding.txtGrades
                    )

                }

            }

        }

    }

    override fun getItemCount(): Int = forecastByHourList.size

    private inner class ForecastViewHolder(
        val binding: ForecastItemViewBinding,
        val context: Context
    ) : BaseViewHolder<Hour>(binding.root) {

        override fun bind(item: Hour) {
            /*
                bind(...) method creates each element to "draw" in RecyclerView.
            */
            val hour = splitDate(item.time, 1)

            val formattedHour = getAnyHourFormatted(
                splitHour(hour, 0),
                splitHour(hour, 1),
                context
            )

            binding.txtHour.text = formattedHour

            Glide.with(context)
                .load("${AppConstants.BASE_IMAGE_URL}${item.condition.icon}")
                .centerCrop()
                .into(binding.imgIcon)

            binding.txtTemperature.text = item.temp_c

        }

    }

}
package com.circleappsstudio.foggyweather.ui.home.adapter

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
import com.circleappsstudio.foggyweather.core.ui.changeForecastByHourCardViewColor
import com.circleappsstudio.foggyweather.core.ui.changeForecastByHourUnselectedCardViewColor
import com.circleappsstudio.foggyweather.data.model.Hour
import com.circleappsstudio.foggyweather.databinding.ForecastItemViewBinding

class ForecastByHourAdapter(
    private val forecastByHourList: List<Hour>,
    private var currentHour: String,
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = ForecastItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ForecastViewHolder(itemBinding, parent.context)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is ForecastViewHolder -> {

                holder.bind(forecastByHourList[position])

                val hour = splitDate(forecastByHourList[position].time)
                val splitHour = splitHour(hour, 0).toInt()

                if (currentHour.toInt() == splitHour) {

                    changeForecastByHourCardViewColor(
                        context = context,
                        primaryCardView = holder.binding.primaryCardView,
                        secondaryCardView = holder.binding.secondaryCardView,
                        hour = holder.binding.txtHour,
                        temperature = holder.binding.txtTemperature,
                        grades = holder.binding.txtGrades
                    )

                } else {

                    changeForecastByHourUnselectedCardViewColor(
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

            binding.txtTemperature.text = item.temp_c

        }

    }

}
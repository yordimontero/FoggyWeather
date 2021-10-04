package com.circleappsstudio.foggyweather.ui.current.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.core.splitDate
import com.circleappsstudio.foggyweather.data.model.Hour
import com.circleappsstudio.foggyweather.databinding.ForecastItemViewBinding

class ForecastAdapter(
    private val forecastByHourList: List<Hour>
): RecyclerView.Adapter<BaseViewHolder<*>>() {

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
            }
        }

    }

    override fun getItemCount(): Int = forecastByHourList.size

    private inner class ForecastViewHolder(
        val binding: ForecastItemViewBinding,
        val context: Context
    ): BaseViewHolder<Hour>(binding.root) {

        override fun bind(item: Hour) {

            binding.txtHour.text = splitDate(item.time)

            Glide.with(context)
                .load("${AppConstants.BASE_IMAGE_URL}${item.condition.icon}")
                .centerCrop()
                .into(binding.imgIcon)

            binding.txtTemperature.text = item.temp_c

        }

    }

}
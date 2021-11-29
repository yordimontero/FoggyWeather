package com.circleappsstudio.foggyweather.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.data.model.ForecastDay
import com.circleappsstudio.foggyweather.databinding.Forecast3DaysItemViewBinding

class Forecast3DaysAdapter(
    val forecast3DaysList: List<ForecastDay>
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = Forecast3DaysItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Forecast3DaysViewHolder(itemBinding, parent.context)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

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

            binding.txtDate.text = item.date

            binding.txtText.text = item.day.condition.text

            Glide.with(context)
                .load("${AppConstants.BASE_IMAGE_URL}${item.day.condition.icon}")
                .centerCrop()
                .into(binding.imgIcon)

            binding.txtMaxTemp.text = item.day.maxtemp_c.toString()
            binding.txtMinTemp.text = item.day.mintemp_c.toString()

        }

    }

}
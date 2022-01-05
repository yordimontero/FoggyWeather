package com.circleappsstudio.foggyweather.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.data.model.Locations
import com.circleappsstudio.foggyweather.databinding.AutocompleteItemViewBinding

class AutocompleteAdapter(
    private val autocompleteList: List<Locations>,
    private val itemClickListener: OnLocationClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnLocationClickListener {
        fun onLocationClick(locations: Locations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = AutocompleteItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AutocompleteViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is AutocompleteViewHolder -> {
                holder.bind(autocompleteList[position])
            }
        }

    }

    override fun getItemCount(): Int = autocompleteList.size

    private inner class AutocompleteViewHolder(
        val binding: AutocompleteItemViewBinding
    ): BaseViewHolder<Locations>(binding.root) {

        override fun bind(item: Locations) {

            //binding.txtSearchItem.text = item.name
            binding.txtSearchItem.text = "${item.name}, ${item.region}, ${item.country}"

            binding.txtSearchItem.setOnClickListener {
                itemClickListener.onLocationClick(item)
            }

        }

    }

}
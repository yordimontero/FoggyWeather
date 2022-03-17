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
        /*
            Interface to set click function in each item from RecyclerView.
        */
        fun onLocationClick(locations: Locations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*
            onCreateViewHolder returns class that binds each RecyclerView element.
            It inflates the layout that will display the data.
        */
        val itemBinding = AutocompleteItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AutocompleteViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        /*
            Each RecyclerView element binds.
        */
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
            /*
                bind(...) method creates each element to "draw" in RecyclerView.
            */
            binding.txtSearchItem.text = "${item.name}, ${item.region}, ${item.country}"

            binding.txtSearchItem.setOnClickListener {
                // Click on RecyclerView item.
                itemClickListener.onLocationClick(item)
            }

        }

    }

}
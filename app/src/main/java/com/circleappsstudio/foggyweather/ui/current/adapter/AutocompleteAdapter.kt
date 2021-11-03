package com.circleappsstudio.foggyweather.ui.current.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.circleappsstudio.foggyweather.core.BaseViewHolder
import com.circleappsstudio.foggyweather.data.model.Location
import com.circleappsstudio.foggyweather.databinding.AutocompleteItemViewBinding

class AutocompleteAdapter(
    val autocompleteList: List<Location>
): RecyclerView.Adapter<BaseViewHolder<*>>() {

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
    ): BaseViewHolder<Location>(binding.root) {

        override fun bind(item: Location) {
            binding.txtSearchItem.text = item.name
        }

    }

}
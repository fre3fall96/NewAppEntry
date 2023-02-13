package com.example.newappentry.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newappentry.databinding.FilterListItemBinding
import com.example.newappentry.network.FilterButton

class FilterAdapter(
    private val buttonItemList: List<FilterButton>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {


    inner class FilterViewHolder(val binding: FilterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding =
            FilterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        with(holder) {
            with(buttonItemList[position]) {
                binding.BTFilterOptions.text = title
                binding.BTFilterOptions.setOnClickListener {
                    onItemClick(title)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return buttonItemList.size
    }

}
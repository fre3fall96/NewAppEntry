package com.example.newappentry.overview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newappentry.R
import com.example.newappentry.databinding.FilterListItemBinding
import com.example.newappentry.network.ButtonList
import com.example.newappentry.network.ObjectArticleInfo

class FilterAdapter(private val buttonItemList: List<ButtonList>,
                    private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(){
    inner class FilterViewHolder(val binding : FilterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        //val buttonItem: Button = view.findViewById(R.id.filter_button)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //return ViewHolder(LayoutInflater.from(context).inflate(R.layout.filter_list_item,parent,false))

        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        with(holder){
            with(buttonItemList[position]){
                binding.filterButton.text = title
                binding.filterButton.setOnClickListener{
                    onItemClick(title)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return buttonItemList.size
    }

}
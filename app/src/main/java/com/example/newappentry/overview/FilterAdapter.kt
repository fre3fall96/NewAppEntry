package com.example.newappentry.overview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.newappentry.R
import com.example.newappentry.network.ButtonList
import com.example.newappentry.network.ObjectArticleInfo

class FilterAdapter(private val context: Context,
                    private val list: ArrayList<ButtonList>) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val buttonItem: Button = view.findViewById(R.id.filter_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.filter_list_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.buttonItem.setText(data.title)

    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class OnClickListener(val clickListener: (buttonList: ButtonList) -> Unit) {
        fun onClick(buttonList: ButtonList) = clickListener(buttonList)
    }
}
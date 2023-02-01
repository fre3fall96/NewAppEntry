package com.example.newappentry.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.newappentry.databinding.NewListItemBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import com.example.newappentry.network.ObjectArticleInfo

class NewsAdapter(val onItemClick:(objectArticleInfo : ObjectArticleInfo, position:Int)->Unit) : ListAdapter<ObjectArticleInfo, NewsAdapter.NewsHolder>(
    DiffCallBack
) {

    class NewsHolder(private var binding: NewListItemBinding, val onItemClick:(objectArticleInfo : ObjectArticleInfo,position:Int)->Unit):RecyclerView.ViewHolder(binding.root){
        fun bind(objectArticleInfo : ObjectArticleInfo){
            binding.newsObject = objectArticleInfo
            binding.executePendingBindings()

            binding.newsConstraint.setOnClickListener {
                // go
                onItemClick.invoke(objectArticleInfo, adapterPosition)
            }

            binding
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ObjectArticleInfo>(){
        override fun areItemsTheSame(
            oldItem: ObjectArticleInfo,
            newItem: ObjectArticleInfo
        ): Boolean {
            return oldItem.description == newItem.description && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: ObjectArticleInfo,
            newItem: ObjectArticleInfo
        ): Boolean {
            return oldItem.url == newItem.url && oldItem.content == newItem.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsHolder {
        return NewsHolder(NewListItemBinding.inflate(LayoutInflater.from(parent.context)),onItemClick)
    }
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = getItem(position)
        holder.bind(newsItem)
    }
}
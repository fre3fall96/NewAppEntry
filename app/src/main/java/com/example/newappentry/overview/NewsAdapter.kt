package com.example.newappentry.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newappentry.databinding.NewsListItemBinding
import com.example.newappentry.network.ObjectArticleInfo

class NewsAdapter (val onItemClick:(objectArticleInfo : ObjectArticleInfo, position:Int)->Unit) : ListAdapter<ObjectArticleInfo, NewsAdapter.NewsHolder>(
DiffCallBack) {

    class NewsHolder(private var binding: NewsListItemBinding, val onItemClick:(objectArticleInfo : ObjectArticleInfo,position:Int)->Unit):RecyclerView.ViewHolder(binding.root){
        fun bind(objectArticleInfo : ObjectArticleInfo){
            binding.newsObject = objectArticleInfo
            binding.executePendingBindings()
            binding.newsImage.setOnClickListener {
                onItemClick.invoke(objectArticleInfo, adapterPosition)
            }

            binding.newsConstraint.setOnClickListener {
                // go
                onItemClick.invoke(objectArticleInfo, adapterPosition)
            }

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(NewsListItemBinding.inflate(LayoutInflater.from(parent.context)),onItemClick)
    }
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = getItem(position)
        holder.bind(newsItem)
    }

}
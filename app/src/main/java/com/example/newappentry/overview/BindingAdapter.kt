package com.example.newappentry.overview

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newappentry.R
import com.example.newappentry.network.ButtonList
import com.example.newappentry.network.ObjectArticleInfo

//this binding adapters uses nullable parameter
//this means that if the parameter is not found, there will not be null exception error

//this binds to fragment_overview recylerview fragment with attribute listData
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ObjectArticleInfo>?){
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

//this binds to news_list_item fragment with imageview and attribute imageUrl
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        val imgUrl = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUrl){
            placeholder(R.drawable.loading_animation)
        }
    }

}
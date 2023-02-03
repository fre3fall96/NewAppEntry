package com.example.newappentry.overview

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newappentry.R
import com.example.newappentry.network.ObjectArticleInfo

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ObjectArticleInfo>?){
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        val imgUrl = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUrl){
            placeholder(R.drawable.loading_animation)
        }
    }

}
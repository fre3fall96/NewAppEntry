package com.example.newappentry.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentOverviewBinding
import com.example.newappentry.network.ObjectArticleInfo

class OverviewFragment : Fragment(){

    private val viewModel: OverviewViewModel by viewModels()
    fun getDetails(newsObject:ObjectArticleInfo){
        println(newsObject)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.newsLayout.adapter = NewsAdapter({article, position ->
            Log.i("SSS", " "+position + " " + article.title)
            val newsDetail = NewsDetailFragment()
            //create bundle
            val newsBundle =  Bundle()
            newsDetail.arguments = newsBundle
            newsBundle.putString("title", article.title)
            newsBundle.putString("content", article.content)
            newsBundle.putString("url", article.urlToImage)
            newsBundle.putString("author", article.author)
            newsBundle.putString("published", article.publishedAt)
            newsBundle.putString("urlToNews", article.url)

            //omg this took so long to understand
            //this replace whatever that is inside overviewFragment with the new fragment
            this.activity?.supportFragmentManager?.beginTransaction()?.
            replace(R.id.overviewFragment, newsDetail)?.addToBackStack(null)?.commit()
        })

        return binding.root
    }
}

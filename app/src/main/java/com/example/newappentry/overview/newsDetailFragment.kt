package com.example.newappentry.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import coil.load
import com.example.newappentry.R
import com.example.newappentry.databinding.FragmentNewsDetailsBinding

class NewsDetailFragment: Fragment() {

    private var binding : FragmentNewsDetailsBinding? = null

    fun shareContent(){
        val formattedText = "News: "+binding?.contentDetails?.text+" By : "+binding?.authorDetails?.text
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, formattedText)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        //loading of bundle
        println(this.arguments?.getString("body"))
        val newsTitle:TextView = fragmentBinding.titleDetails
        newsTitle.setText(this.arguments?.getString(("title")))

        val newsContent:TextView = fragmentBinding.contentDetails
        newsContent.setText(this.arguments?.getString(("content")))

        val newsImage:ImageView = fragmentBinding.newsImageDetails
        println(this.arguments?.getString(("url")))
        newsImage.load(this.arguments?.getString(("url"))?.toUri())

        val newsAuthor:TextView = fragmentBinding.authorDetails
        newsAuthor.setText("Author: "+this.arguments?.getString(("author")))

        val newsPublishedAt:TextView = fragmentBinding.publishedAtDetails
        newsPublishedAt.setText(this.arguments?.getString(("published")))

        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //updating textview
        binding?.newsDetailFragment = this
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
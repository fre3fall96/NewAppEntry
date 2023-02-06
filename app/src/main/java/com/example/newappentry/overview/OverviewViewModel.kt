package com.example.newappentry.overview

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappentry.network.ObjectArticleInfo
import com.example.newappentry.network.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    private var _news = MutableLiveData<List<ObjectArticleInfo>>()
    val news: LiveData<List<ObjectArticleInfo>> =_news

    init {
        getNewsArticle()
    }

    fun prepareBundle(articleInfo : ObjectArticleInfo): Bundle {
        val newsBundle = Bundle()
        newsBundle.putString("title", articleInfo.title)
        newsBundle.putString("content", articleInfo.content)
        newsBundle.putString("url", articleInfo.urlToImage)
        newsBundle.putString("author", articleInfo.author)
        newsBundle.putString("published", articleInfo.publishedAt)
        newsBundle.putString("urlToNews", articleInfo.url)
        return newsBundle
    }

    private fun getNewsArticle(){
        viewModelScope.launch{
            var newsInfo =
            try{
                _news.value = repository.getNews().articles
            } catch (e: java.lang.Exception){
                Log.d("TAG", e.message?: "")
            }
        }
    }

    fun searchArticle(){
        //val searchTextView : TextView = view.findViewById<EditText>(R.id.search_bar)
    }

}
package com.example.newappentry.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newappentry.network.*
import kotlinx.coroutines.launch


class OverviewViewModel : ViewModel(){
    private var _news = MutableLiveData<List<ObjectArticleInfo>>()
    val news: LiveData<List<ObjectArticleInfo>> =_news

    init {
        getNewsArticle()
    }

    private fun getNewsArticle(){
        viewModelScope.launch{
            var newsInfo = newsAPI.retrofitService.getNews()
            try{
                _news.value = newsInfo.articles
            } catch (e: java.lang.Exception){
                Log.d("TAG", e.message?: "")
            }
        }
    }
}
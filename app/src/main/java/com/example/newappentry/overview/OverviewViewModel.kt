package com.example.newappentry.overview

import android.graphics.ColorSpace
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.*
import com.example.newappentry.R
import com.example.newappentry.network.ButtonList
import com.example.newappentry.network.ObjectArticleInfo
import com.example.newappentry.network.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    lateinit var allResults : List<ObjectArticleInfo>
    private var _news = MutableLiveData<List<ObjectArticleInfo>>()
    val news: LiveData<List<ObjectArticleInfo>> =_news
    val staticNews = news
    val categories : List<String> = listOf("Apple","Tesla","Finance","Google","Singapore")

    init {
        getNewsArticle("Google")
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

    fun getNewsArticle(category:String){
        Log.d("thisthis", "clickeddddd")
        viewModelScope.launch{
            var newsInfo =
            try{
                allResults = repository.getNews(category).articles
                var filteredList = ArrayList<ObjectArticleInfo>()

                for(i in 0..(allResults.size)-1){

                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(allResults.get(i).publishedAt)
                    val formatter = SimpleDateFormat("EEEE, dd-MMM-yyyy")
                    // val formatter = SimpleDateFormat("EEEE dd-MMM-yyyy HH:mm")
                    val dt = formatter.format(date)
                    allResults.get(i).publishedAt = dt
                    filteredList.add(allResults.get(i))
                }

                _news.value = filteredList
            } catch (e: java.lang.Exception){
                Log.d("TAG", e.message?: "")
            }
        }
    }

    fun searchArticle(query : String?){
        //   var results = news.value!!.filter {
        var search : String = query.toString()
        var filteredList = ArrayList<ObjectArticleInfo>()
        for(i in 0..(allResults.size)-1){
            if ((allResults.get(i)?.author?.contains(search) == true) or (allResults.get(i)?.content?.contains(search) == true)){
                filteredList.add(allResults.get(i))
            }
        }
        _news.value = filteredList
    }

    fun fetchList(): ArrayList<ButtonList> {
        val list = arrayListOf<ButtonList>()

        for (i in categories) {
            val model = ButtonList(i)
            list.add(model)
        }
        return list
    }

}
package com.example.newappentry.overview

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.newappentry.Constant
import com.example.newappentry.network.FilterButton
import com.example.newappentry.network.ObjectArticleInfo
import com.example.newappentry.network.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    lateinit var allResults : List<ObjectArticleInfo>
    private var _news = MutableLiveData<List<ObjectArticleInfo>>()
    val news: LiveData<List<ObjectArticleInfo>> =_news
    val staticNews = news
    val categories : List<String> = listOf("Apple","Tesla","Finance","Google","Singapore")
    var filterType : String = Constant.LATEST
    var currentSearch : String = Constant.DEFAULT_SEARCH
    var currentDate = getTodayDate()

    init {
        getNewsArticle(Constant.DEFAULT_SEARCH, Constant.LATEST, currentDate)
    }

    fun prepareBundle(articleInfo : ObjectArticleInfo): Bundle {
        val newsBundle = Bundle()
        newsBundle.putString(Constant.TITLE, articleInfo.title)
        newsBundle.putString(Constant.CONTENT, articleInfo.content)
        newsBundle.putString(Constant.URL, articleInfo.urlToImage)
        newsBundle.putString(Constant.AUTHOR, articleInfo.author)
        newsBundle.putString(Constant.PUBLISH, articleInfo.publishedAt)
        newsBundle.putString(Constant.URL_TO_NEWS, articleInfo.url)
        return newsBundle
    }

    fun getNewsArticle(category:String, sortby:String, currentDate:String){
        viewModelScope.launch{
            var newsInfo =
            try{
                allResults = repository.getNews(category, sortby, currentDate).articles
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

    fun fetchList(): ArrayList<FilterButton> {
        val list = arrayListOf<FilterButton>()

        for (i in categories) {
            val model = FilterButton(i)
            list.add(model)
        }
        return list
    }

    fun getTodayDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(Calendar.getInstance().time)
        return current
    }

}
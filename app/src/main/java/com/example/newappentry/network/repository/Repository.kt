package com.example.newappentry.network.repository

import com.example.newappentry.network.ObjectNewsInfo
import com.example.newappentry.network.newsApiService
import retrofit2.http.GET
import retrofit2.http.Query

class Repository(private val newsApiService: newsApiService) {
    suspend fun getNews(category:String, sorting: String, fromDate: String) = newsApiService.getNews(category, sorting, fromDate)

}

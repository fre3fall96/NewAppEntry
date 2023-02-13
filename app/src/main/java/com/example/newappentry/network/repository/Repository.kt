package com.example.newappentry.network.repository

import com.example.newappentry.network.NewsApiService

class Repository(private val newsApiService: NewsApiService) {
    suspend fun getNews(category:String, sorting: String, fromDate: String) = newsApiService.getNews(category, sorting, fromDate)

}

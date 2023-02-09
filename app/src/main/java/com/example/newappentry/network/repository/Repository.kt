package com.example.newappentry.network.repository

import com.example.newappentry.network.newsApiService

class Repository(private val newsApiService: newsApiService) {
    suspend fun getNews(category:String, sorting: String, fromDate: String) = newsApiService.getNews(category, sorting, fromDate)
}
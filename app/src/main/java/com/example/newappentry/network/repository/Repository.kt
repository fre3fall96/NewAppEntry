package com.example.newappentry.network.repository

import com.example.newappentry.network.newsApiService

class Repository(private val newsApiService: newsApiService) {
    suspend fun getNews(test:String) = newsApiService.getNews(test)
}
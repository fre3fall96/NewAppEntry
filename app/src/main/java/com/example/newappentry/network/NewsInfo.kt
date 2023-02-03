package com.example.newappentry.network

//this is the deepest nested list there is
data class NewsInfo(val id: String?, val name: String)

//the list that we use to retrieve the actual news article
data class ObjectArticleInfo(val author: String?,
                             val title: String?,
                             val description: String?,
                             val url: String,
                             val urlToImage: String?,
                             val publishedAt: String,
                             val content: String)

//the shallowest list that contains all the articles
data class ObjectNewsInfo(val status: String, val totalResults:String, val articles:List<ObjectArticleInfo>)

package com.example.newappentry.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //this is a get request that will be appended to the end of the base url
    //10faeabe3a944f79928e541246918154
    //b6382435cc9442d092e3697aa619e98a
    @GET("everything?language=en&apiKey=10faeabe3a944f79928e541246918154")
    suspend fun getNews(@Query("q") category:String, @Query("sortBy") sorting: String, @Query("from") dateTime : String):ObjectNewsInfo
}
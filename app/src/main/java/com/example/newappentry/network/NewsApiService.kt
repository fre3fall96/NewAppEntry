package com.example.newappentry.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//private const val newsAPI = "https://newsapi.org/v2/everything?q=Apple&from=2023-01-26&sortBy=popularity&apiKey=b6382435cc9442d092e3697aa619e98a"
// Declaring the baseURL
private const val BASEURL = "https://newsapi.org/v2/"
//This uses the retrofit lib to talk to the newsapi
//I do not have a good understanding of the retrofit library but if required, I will read up on it
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASEURL)
    .build()

interface newsApiService {
    //this is a get request that will be appended to the end of the base url
    @GET("everything?q=Apple&from=2023-01-26&sortBy=popularity&apiKey=b6382435cc9442d092e3697aa619e98a")
    suspend fun getNews():ObjectNewsInfo
}

object newsAPI{
    //initialisation of retrofit service without any initial value
    val retrofitService : newsApiService by lazy {
        retrofit.create(newsApiService::class.java)
    }
}
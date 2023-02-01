package com.example.newappentry.network

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

//data class newsInfo(val source: String, val author: String, val title: String, val description: String, val url: String, val urlToImage: String, val publishedAt: String, val content: String) {


//this is the deepest nested list there is
data class NewsInfo(val id: String?, val name: String)

//the list that we use to retrieve the actual news article
data class ObjectArticleInfo(val author: String?,
                             val title: String?,
                             val description: String?,
                             val url: String,
                             val urlToImage: String?,
                             val publishedAt: String,
                             val content: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString() ?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ObjectArticleInfo> {
        override fun createFromParcel(parcel: Parcel): ObjectArticleInfo {
            return ObjectArticleInfo(parcel)
        }

        override fun newArray(size: Int): Array<ObjectArticleInfo?> {
            return arrayOfNulls(size)
        }
    }
}

//the shallowest list that contains all the articles
data class ObjectNewsInfo(val status: String, val totalResults:String, val articles:List<ObjectArticleInfo>)


//data class objectArticleInfo(val source: newsInfo,val author: String?, val title: String?, val description: String?, val url: String, val urlToImage: String?, val publishedAt: String, val content: String){}

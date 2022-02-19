package com.lupa.newsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsData {

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null
}
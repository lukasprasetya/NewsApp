package com.lupa.newsapp.network

import com.lupa.newsapp.model.NewsData
import io.reactivex.Observable

class Repository(private val apiService: ApiService) {
    fun getNews(q:String, apikey:String): Observable<NewsData> {
        return apiService.getNews(q, apikey)
    }
}
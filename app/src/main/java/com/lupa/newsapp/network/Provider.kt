package com.lupa.newsapp.network

object Provider {

    fun ProviderRepository():Repository{
        return  Repository(ApiService.create())
    }
}
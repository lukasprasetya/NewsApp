package com.lupa.newsapp.network


import com.lupa.newsapp.BuildConfig
import com.lupa.newsapp.model.NewsData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    fun getNews(@Query("q") news: String, @Query("apiKey") apikey: String): Observable<NewsData>

    companion object Factory {
        fun create(): ApiService {

            val logging = HttpLoggingInterceptor()

            logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
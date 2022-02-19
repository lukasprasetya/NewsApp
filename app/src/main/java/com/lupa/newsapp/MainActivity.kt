package com.lupa.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lupa.newsapp.adapter.NewsAdapter
import com.lupa.newsapp.model.Article
import com.lupa.newsapp.network.Provider
import com.lupa.newsapp.viewmodel.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_failed.*
import kotlinx.android.synthetic.main.layout_loading.*

class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private var dataListNews: MutableList<Article?> = mutableListOf()
    private val compositeDisposable = CompositeDisposable()
    private val repository = Provider.ProviderRepository()
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        newsAdapter = NewsAdapter(dataListNews, applicationContext) {
            val intentDetail = Intent(applicationContext, DetailActivity::class.java)
            intentDetail.putExtra(getString(R.string.image), it.urlToImage)
            intentDetail.putExtra(getString(R.string.intent_title), it.title)
            intentDetail.putExtra(getString(R.string.source), it.source?.name)
            intentDetail.putExtra(getString(R.string.publishedAt), it.publishedAt)
            intentDetail.putExtra(getString(R.string.description), it.description)
            startActivity(intentDetail)
        }

        linearLayoutManager = LinearLayoutManager(applicationContext)
        rv_main.layoutManager = linearLayoutManager
        rv_main.hasFixedSize()
        rv_main.adapter = newsAdapter

        newsViewModel = ViewModelProviders.of(
            this,
            NewsViewModel.ViewModelNewsFactory(
                compositeDisposable,
                repository,
                AndroidSchedulers.mainThread(),
                Schedulers.io()
            )
        ).get(NewsViewModel::class.java)
        newsViewModel.setListNews()
        newsViewModel.getListNews().observe(this, getNews)
        swipe_list_news.setOnRefreshListener {
            swipe_list_news.isRefreshing = true
            newsViewModel.setListNews()
            newsViewModel.getListNews().observe(this, getNews)
        }
    }

    private val getNews = Observer<MutableList<Article>> { newsItems ->
        if (newsItems != null) {
            Log.d("TAG", ": ARITICLEE " + newsItems.size)
            dataListNews.clear()
            rv_main.visibility = View.VISIBLE
            loading_page.visibility = View.GONE
            error_page.visibility = View.GONE
            if (newsItems.size > 0) {
                val dataListNews: MutableList<Article?> = mutableListOf()
                newsItems.let { dataListNews.addAll(it) }
                newsAdapter.addData(dataListNews)
            }
        } else {
            rv_main.visibility = View.GONE
            loading_page.visibility = View.VISIBLE
            error_page.visibility = View.VISIBLE
        }
        swipe_list_news.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
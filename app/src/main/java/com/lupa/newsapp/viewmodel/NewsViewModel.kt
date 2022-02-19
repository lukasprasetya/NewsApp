package com.lupa.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lupa.newsapp.model.Article
import com.lupa.newsapp.network.Repository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel (

    private val compositeDisposable: CompositeDisposable,
    private val repository: Repository,
    private val backgroundScheduler: Scheduler,
    private val mainScheduler: Scheduler
    ) : ViewModel() {
        private var listNews = MutableLiveData<MutableList<Article>>()

        fun setListNews() {
            compositeDisposable.add(
                repository.getNews("tesla","eecab206ed2d4be4ad1e0305f6650319")
                    .observeOn(backgroundScheduler)
                    .subscribeOn(mainScheduler)
                    .subscribe({ NewsViewModel ->
                        listNews.postValue(NewsViewModel.articles as java.util.ArrayList<Article>?)
                    }, { error ->
                        println("error message " + error.message)
                        listNews.postValue(null)
                    }
                    )
            )
        }

        fun getListNews(): LiveData<MutableList<Article>> {
            return listNews
        }

        class ViewModelNewsFactory(
            private val compositeDisposable: CompositeDisposable,
            private val repository: Repository,
            private val backgroundScheduler: Scheduler,
            private val mainScheduler: Scheduler
        ) : ViewModelProvider.NewInstanceFactory() {
            @SuppressWarnings("unchecked")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsViewModel(
                    compositeDisposable,
                    repository,
                    backgroundScheduler,
                    mainScheduler
                ) as T
            }
        }
}
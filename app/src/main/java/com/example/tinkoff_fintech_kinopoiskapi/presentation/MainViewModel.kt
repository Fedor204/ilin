package com.example.tinkoff_fintech_kinopoiskapi.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tinkoff_fintech_kinopoiskapi.domain.Movie
import com.example.tinkoff_fintech_kinopoiskapi.utils.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean>
        get() = _errorMessage

    private val compositeDisposable = CompositeDisposable()



    private var page = 1

    companion object {
        const val TAG = "MainViewModel"
    }

   init {
        loadMovies()
    }



    fun loadMovies() {
        _errorMessage.value = false
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        val disposable = ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }
            .doAfterTerminate {
                _isLoading.value = false
            }
            .subscribe({
                val loadedMovies = movies.value
                if (loadedMovies != null) {
                    loadedMovies as MutableList<Movie>
                    loadedMovies.addAll(it.films)
                    _movies.value = loadedMovies
                    Log.d(TAG, it.toString())
                } else {
                    _movies.value = it.films
                }
                page++
            }, {
                Log.d(TAG, it.toString())
                _errorMessage.value = true
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
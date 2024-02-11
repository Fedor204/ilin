package com.example.tinkoff_fintech_kinopoiskapi.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tinkoff_fintech_kinopoiskapi.domain.DescriptionResponse
import com.example.tinkoff_fintech_kinopoiskapi.utils.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val _description = MutableLiveData<DescriptionResponse>()
    val description : LiveData<DescriptionResponse>
            get() = _description


    fun loadDescription(movieId: Int) {
        val disposable = ApiFactory.apiService.loadDescription(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _description.value = it
            }, {
                Log.d("MovieDetailViewModel", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
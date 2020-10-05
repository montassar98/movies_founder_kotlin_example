package com.vneuron.mvpkotlinexample.model

import com.vneuron.mvpkotlinexample.util.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteDataSource {

    fun searchResultsObservable(query: String): Observable<TmdbResponse>{
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun searchResultsObservableByPages(query: String,page: Int): Observable<TmdbResponse>{
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
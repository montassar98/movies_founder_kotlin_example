package com.vneuron.mvpkotlinexample.home

import android.util.Log
import com.vneuron.mvpkotlinexample.model.RemoteDataSource
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomePresenter(private val view: HomeContract.ViewInterface, private val dataSource: RemoteDataSource) : HomeContract.PresenterInterface{

    private val mList: MutableList<Movie> = emptyList<Movie>().toMutableList()
    private var count = 0;
    private var totalPages:Int = 0

    private val compositeDisposable = CompositeDisposable()
    val retrieveDataDisposable: (String) -> Observable<TmdbResponse> = {
        dataSource.searchResultsObservable(it)
    }

    val retrieveDataDisposableByPages: (String, Int) -> Observable<TmdbResponse> = { s,k ->
        dataSource.searchResultsObservableByPages(s,k)
    }
    private val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>(){
            override fun onNext(t: TmdbResponse) {
                Log.d(TAG, "onNext: ${t.totalResults}  ${t.results?.size}")
                totalPages = t.totalPages!!.or(0)
                view.initMoviesRecyclerView(t.results?.toMutableList()!!)
                retrieveMultipleData(t.totalPages)

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: ", e)
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ${mList.size}")

            }

        }
    private val observerForRestData: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>(){
            override fun onNext(t: TmdbResponse) {
                Log.d(TAG, "onNext: ${t.totalResults}  ${t.results?.size}")
                view.initMoviesRecyclerView(t.results?.toMutableList()!!)

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: ", e)
            }

            override fun onComplete() {

                Log.d(TAG, "onComplete: composite disposable size = ${++count}")
                if (count == 10 - 1) {
                    Log.d(TAG, "all data retrieved")
                    view.displayRecyclerState(true)
                    view.progressBarState(false)
                }
            }

        }

    override fun retrieveData() {
        val allDataDisposable:Disposable = retrieveDataDisposable("a")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(allDataDisposable)



    }

    private fun retrieveMultipleData(totalPages: Int?) {
        if (totalPages !=  null && totalPages > 1){
            for (i in 2..10){
                val dataDisposable:DisposableObserver<TmdbResponse> = retrieveDataDisposableByPages("a",i)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observerForRestData)
                compositeDisposable.add(dataDisposable)

            }

        }
    }

    override fun stop() {
        Log.d(TAG, "Disposable stopped")
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }


    companion object{
        private const val TAG = "HomePresenter"
    }

}
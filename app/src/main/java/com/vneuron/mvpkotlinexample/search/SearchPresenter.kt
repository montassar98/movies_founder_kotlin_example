package com.vneuron.mvpkotlinexample.search

import android.util.Log
import com.vneuron.mvpkotlinexample.model.RemoteDataSource
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class SearchPresenter(private var viewInterface: SearchContract.ViewInterface, private var dataSource: RemoteDataSource):SearchContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()
    val searchResultsObservable: (String) -> Observable<TmdbResponse> = { query -> dataSource.searchResultsObservable(query)}
    private val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {
            override fun onNext(t: TmdbResponse) {
                Log.d(TAG, "onNext ${t.totalResults}")
                Log.d(TAG, "onNext ${t.results.toString()}")
                viewInterface.initSearchRecyclerView(moviesList = t.results!!)
                //viewInterface.let { t.results?.let { it1 -> it.initSearchRecyclerView(it1) } }
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "Error fetching movie data.  ", e)
                viewInterface.showMessage("error fetching movie data")
            }

            override fun onComplete() {
                Log.d(TAG, "completed")
                viewInterface.hideProgressBar()
            }

        }


    override fun getSearchResults(query: String) {
        val searchResultDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(searchResultDisposable)
    }

    override fun saveMovieToDatabase(movie: Movie) {

    }

    override fun stop() {
        compositeDisposable.clear()
    }

    companion object{
        private const val TAG = "SearchPresenter"
    }
}
package com.vneuron.mvpkotlinexample.details

import android.content.Context
import android.util.Log
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.model.Movie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsPresenter(private var view:DetailsContract.ViewInterface,
                       private val movieRepository: MovieRepository): DetailsContract.PresenterInterface,CoroutineScope {

    private lateinit var movie: Movie
    private var ioContext: CoroutineContext = Dispatchers.IO
    private val uiContext: CoroutineContext = Dispatchers.Main
    override val coroutineContext: CoroutineContext = Job() + ioContext



    override fun passMovieObjectToPresenter(movie: Movie) {
        this.movie = movie
    }

    override fun saveToWishList() {
        launch {
            val query:Long
            withContext(ioContext){
                Log.d(TAG, "Coroutine context = $uiContext")
                 query = movieRepository.insertMovieToWishList(movie)
                Log.d(TAG, "inserted ? = $query")
            }
            withContext(uiContext){
                view.displayMessage(query.toString())
            }

        }
    }


    companion object{
        private const val TAG = "DetailsPresenter"
    }

}
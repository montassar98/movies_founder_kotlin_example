package com.vneuron.mvpkotlinexample.main

import android.util.Log
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.model.MovieTest
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter (val viewInterface: MainContract.ViewInterface,
                     private val movieRepository: MovieRepository
): MainContract.PresenterInterface,CoroutineScope{

    private var ioContext: CoroutineContext = Dispatchers.IO
    private val uiContext: CoroutineContext = Dispatchers.Main
    override val coroutineContext: CoroutineContext = Job() + ioContext




    override  fun insertMovie(movieTest: MovieTest) {

        Log.d(TAG, "insertMovie: $movieTest")

        launch {
            withContext(ioContext){
                Log.d(TAG, "Coroutine context = $uiContext")
               val query:Long = movieRepository.insertMovie(movieTest)
                Log.d(TAG, "inserted ? = $query")
            }

        }
    }

    override fun unsubscribe() {
        coroutineContext.cancel()
        ioContext.cancel()
        uiContext.cancel()
    }

    companion object{
        private const val TAG = "MainPresenter"
    }


}
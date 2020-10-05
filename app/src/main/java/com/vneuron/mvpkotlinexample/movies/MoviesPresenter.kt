package com.vneuron.mvpkotlinexample.movies

import com.vneuron.mvpkotlinexample.dao.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MoviesPresenter(
    private val viewInterface: MoviesContract.ViewInterface,
    private val moviesRepository: MovieRepository
):MoviesContract.PresenterInterface,CoroutineScope {

    private val ioContext = Dispatchers.IO
    private val uiContext = Dispatchers.Main
    override val coroutineContext: CoroutineContext
        get() = Job() + ioContext

    override fun retrieveAllMovies(){
        launch {
            withContext(ioContext){
                //val movies:List<Mov> = moviesRepository.getAllMovies()
                //viewInterface.initRecyclerView(movies)
            }
        }


    }

    override fun deleteAllMovies() {
        launch {
            withContext(ioContext){
                moviesRepository.deleteAllMovies()
            }
        }
        viewInterface.initRecyclerView(movieTests = emptyList())
    }
}
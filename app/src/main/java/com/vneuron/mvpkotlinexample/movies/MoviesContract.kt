package com.vneuron.mvpkotlinexample.movies

import com.vneuron.mvpkotlinexample.model.MovieTest

class MoviesContract {

    interface PresenterInterface{
        fun retrieveAllMovies()
        fun deleteAllMovies()
    }

    interface ViewInterface{
        fun initRecyclerView(movieTests:List<MovieTest>)
        fun displayMessage(message:String)
        fun displayError(error:String)
    }
}
package com.vneuron.mvpkotlinexample.search

import com.vneuron.mvpkotlinexample.model.Movie

class SearchContract {

    interface ViewInterface{
        fun showMessage(message:String)
        fun showError(error:String)
        fun initSearchRecyclerView(moviesList: List<Movie>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface PresenterInterface{
        fun getSearchResults(query:String)
        fun saveMovieToDatabase(movie :Movie)
        fun stop()
    }
}
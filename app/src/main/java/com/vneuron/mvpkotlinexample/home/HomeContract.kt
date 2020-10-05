package com.vneuron.mvpkotlinexample.home

import com.vneuron.mvpkotlinexample.model.Movie

class HomeContract {

    interface ViewInterface{
        fun displayMessage(message:String)
        fun displayError(error:String)
        fun initMoviesRecyclerView(moviesList: MutableList<Movie>)
        fun notifyRecyclerForDataChange()
        fun displayRecyclerState(isVisible: Boolean)
        fun progressBarState(isVisible:Boolean)
    }

    interface PresenterInterface{
        fun retrieveData()
        fun stop()
    }
}
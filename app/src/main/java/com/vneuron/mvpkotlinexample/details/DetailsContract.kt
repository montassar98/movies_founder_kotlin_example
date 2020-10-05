package com.vneuron.mvpkotlinexample.details

import com.vneuron.mvpkotlinexample.model.Movie

class DetailsContract {

    interface ViewInterface{
        fun displayMessage(message:String)
        fun displayError(error:String)
    }
    interface PresenterInterface{
        fun passMovieObjectToPresenter(movie: Movie)
        fun saveToWishList()
    }
}
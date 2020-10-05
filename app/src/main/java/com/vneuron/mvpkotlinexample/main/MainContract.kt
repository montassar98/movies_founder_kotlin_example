package com.vneuron.mvpkotlinexample.main

import com.vneuron.mvpkotlinexample.model.MovieTest

class MainContract {

    interface ViewInterface{
        fun displayMessage(message:String)
        fun displayError(error:String)
    }

    interface PresenterInterface{
        fun insertMovie(movieTest: MovieTest)
        fun unsubscribe()
    }
}
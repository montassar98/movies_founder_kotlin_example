package com.vneuron.mvpkotlinexample.add

class AddContract {

    interface ViewInterface{
        fun displayMessage(message:String)
        fun displayError(error:String)
    }
    interface PresenterInterface{

    }
}
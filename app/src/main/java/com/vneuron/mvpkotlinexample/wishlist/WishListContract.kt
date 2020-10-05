package com.vneuron.mvpkotlinexample.wishlist

import com.vneuron.mvpkotlinexample.model.Movie

class WishListContract {
    interface ViewInterface{
        fun displayMessage(message:String)
        fun displayError(error:String)
        fun initWishListRecyclerView(moviesList: List<Movie>)
    }
    interface PresenterInterface{
        fun retrieveLocalData()
        fun unsubscribe()
    }
}
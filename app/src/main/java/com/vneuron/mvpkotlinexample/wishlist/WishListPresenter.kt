package com.vneuron.mvpkotlinexample.wishlist

import com.vneuron.mvpkotlinexample.dao.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WishListPresenter(private val view: WishListContract.ViewInterface, private val repository: MovieRepository): WishListContract.PresenterInterface,CoroutineScope {

    private val ioContext = Dispatchers.IO
    private val uiContext = Dispatchers.Main

    override val coroutineContext: CoroutineContext
        get() = Job() + ioContext

    override fun retrieveLocalData() {
        launch {
            withContext(uiContext){
                val moviesList = repository.getAllMovies()
                view.initWishListRecyclerView(moviesList)
            }
        }
    }

    override fun unsubscribe() {
        ioContext.cancel()
        uiContext.cancel()
        coroutineContext.cancel()
    }
}
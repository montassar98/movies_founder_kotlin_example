package com.vneuron.mvpkotlinexample.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.model.Movie
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_wish_list.*

class WishListActivity : AppCompatActivity(),WishListContract.ViewInterface,WishListRecyclerAdapter.ItemClickListener {

    private lateinit var presenter: WishListPresenter
    private lateinit var wishListAdapter: WishListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        setupPresenter()
    }

    private fun setupPresenter() {
        val localDataSource = MovieRepository(applicationContext)
        presenter = WishListPresenter(this, localDataSource)
        wish_list_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.retrieveLocalData()
    }

    override fun initWishListRecyclerView(moviesList: List<Movie>) {
        wishListAdapter = WishListRecyclerAdapter(applicationContext, moviesList, this)
        wish_list_recycler_view.apply {
            adapter = wishListAdapter
        }
        wishListAdapter.notifyDataSetChanged()
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }

    override fun onItemClickListener(view: View, position: Int) {
        Log.d(TAG, "onItemClickListener: ")
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }


    companion object{
        private const val TAG = "WishListActivity"
    }
}
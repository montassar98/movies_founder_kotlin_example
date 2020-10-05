package com.vneuron.mvpkotlinexample.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.add.AddActivity
import com.vneuron.mvpkotlinexample.details.DetailsActivity
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.model.RemoteDataSource
import com.vneuron.mvpkotlinexample.wishlist.WishListActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*


class HomeActivity : AppCompatActivity(), HomeContract.ViewInterface, HomeRecyclerAdapter.Companion.ItemClickListener {

    private lateinit var mPresenter: HomePresenter
    private lateinit var mDataSource: RemoteDataSource
    private lateinit var homeAdapter: HomeRecyclerAdapter
    private var recyclerInitiated: Boolean = false
    private var isRotate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupPresenter()
        hideSearchAnWishListFabButtons()
    }

    private fun setupPresenter(){
        mDataSource = RemoteDataSource()
        mPresenter = HomePresenter(this, mDataSource)
    }

    private fun hideSearchAnWishListFabButtons() {
        init(fabWishList)
        init(fabSearch)
    }

    override fun onStart() {
        super.onStart()
        progressBarState(true)
        mPresenter.retrieveData()
    }



    override fun initMoviesRecyclerView(moviesList: MutableList<Movie>) {

        Log.d(TAG, "initMoviesGridView: ")
        if (!recyclerInitiated)
        {
            Log.d(TAG, "recycler not instantiated yet")
            homeAdapter = HomeRecyclerAdapter(this, moviesList.toMutableList(), this)
            home_movies_recycler.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(applicationContext, 3)
            }
            recyclerInitiated = true
            home_movies_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && fab.isShown)
                        fab.hide();
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        fab.show();
                    super.onScrollStateChanged(recyclerView, newState);
                }
            })
            fab.setOnClickListener {
                isRotate = rotateFab(it, !isRotate)
                if (isRotate){
                    showIn(fabSearch)
                    showIn(fabWishList)
                }else{
                    showOut(fabWishList)
                    showOut(fabSearch)
                }

            }
            fabSearch.setOnClickListener {
                val  intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            fabWishList.setOnClickListener {
                val  intent = Intent(this, WishListActivity::class.java)
                startActivity(intent)
            }
        }else {
            homeAdapter.addItems(moviesList)
            homeAdapter.notifyDataSetChanged()
        }

    }

    override fun notifyRecyclerForDataChange() {
        Log.d(TAG, "notify recycler for data change ")
        homeAdapter.notifyDataSetChanged()

    }

    override fun displayRecyclerState(isVisible: Boolean) {
        Log.d(TAG, "Recycler view state = $isVisible")
        if (isVisible)
            home_movies_recycler.visibility = View.VISIBLE
        else home_movies_recycler.visibility = View.GONE
    }

    override fun progressBarState(isVisible: Boolean) {
        Log.d(TAG, "progress bar state = $isVisible")
        if(isVisible)
            home_progress_bar.visibility = View.VISIBLE
        else home_progress_bar.visibility = View.GONE
    }

    override fun onMovieClickedListener(sharedImageView: ImageView, position: Int, movie: Movie) {
        Log.d(TAG, "item $position clicked  = $movie  ")
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_MOVE_ITEM, movie.posterPath)
        intent.putExtra(EXTRA_MOVE_ITEM2, movie)
        intent.putExtra(
            EXTRA_MOVE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(
                sharedImageView
            )
        )
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            "transition"
        )
        startActivity(intent, options.toBundle())
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }

    companion object{
        private const val TAG = "HomeActivity"
        const val EXTRA_MOVE_ITEM = "extra_movie_item"
        const val EXTRA_MOVE_ITEM2 = "extra_movie_item2"
        const val EXTRA_MOVE_IMAGE_TRANSITION_NAME = "extra_movie_image_transition_name"

        fun rotateFab(v: View, rotate: Boolean): Boolean {
            Log.d(TAG, "rotateFab ")
            v.animate().setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .rotation(if (rotate) 135f else 0f)
            return rotate
        }

        fun showIn(v: View) {
            v.visibility = View.VISIBLE
            v.alpha = 0f
            v.translationY = v.height.toFloat()
            v.animate()
                .setDuration(200)
                .translationY(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }

        fun showOut(v: View) {
            v.visibility = View.VISIBLE
            v.alpha = 1f
            v.translationY = 0f
            v.animate()
                .setDuration(200)
                .translationY(v.height.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        v.visibility = View.GONE
                        super.onAnimationEnd(animation)
                    }
                }).alpha(0f)
                .start()
        }

        fun init(v: View) {
            v.visibility = View.GONE
            v.translationY = v.height.toFloat()
            v.alpha = 0f
        }
    }
}
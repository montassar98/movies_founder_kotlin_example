package com.vneuron.mvpkotlinexample.details

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.chip.Chip
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.home.HomeActivity
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.util.RetrofitClient
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity(),DetailsContract.ViewInterface {

    private lateinit var presenter: DetailsPresenter
    private var movie: Movie = Movie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarToTransparent()
        setContentView(R.layout.activity_details)
        supportPostponeEnterTransition()
        applyingFadeAnimator()
        initPresenter()
        val extras: Bundle? = intent.extras
        movie = extras?.getSerializable(HomeActivity.EXTRA_MOVE_ITEM2) as Movie
        initUi()
        presenter.passMovieObjectToPresenter(movie)
        val imgSharedPoster = findViewById<ImageView>(R.id.img_movie_detail)
        Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + movie.posterPath).into(imgSharedPoster, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })

    }

    private fun initPresenter() {
        val repository: MovieRepository = MovieRepository(applicationContext)
        presenter = DetailsPresenter(this, repository)
        btnWishList.setOnClickListener{
            presenter.saveToWishList()
        }
    }

    private fun initUi() {
        Log.d(TAG, "initUi: $movie")
        txtOverView.text = movie.overview
        txtMovieTitle.text = movie.title
        txtNumStars.text = movie.voteAverage.toString() + "/10"

    }

    private fun applyingFadeAnimator() {
        val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        img_fade.startAnimation(animFadeIn)

    }

    private fun changeStatusBarToTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAfterTransition(this)
    }

    companion object{
        private const val TAG = "DetailsActivity"
    }
}
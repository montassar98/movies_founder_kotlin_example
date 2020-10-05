package com.vneuron.mvpkotlinexample.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.adapter.MovieAdapter
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.main.MainActivity
import com.vneuron.mvpkotlinexample.model.MovieTest
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(),MoviesContract.ViewInterface {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var presenter: MoviesPresenter
    private lateinit var repository: MovieRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        init()
        btnBackToMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun init() {
        repository = MovieRepository(applicationContext)
        presenter = MoviesPresenter(this, repository)
        rvMovies.layoutManager = LinearLayoutManager(this@MoviesActivity)

    }


    override fun initRecyclerView(movieTests: List<MovieTest>) {
        movieAdapter = MovieAdapter(applicationContext, movieTests)
        rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MoviesActivity)
            adapter = movieAdapter
        }
        movieAdapter.notifyDataSetChanged()
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }

    override fun onStart() {
        super.onStart()
        presenter.retrieveAllMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemDelete ->
                presenter.deleteAllMovies()
            else -> displayError("no item selected")
        }
        return true
    }
}
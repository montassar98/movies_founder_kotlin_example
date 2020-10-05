package com.vneuron.mvpkotlinexample.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.dao.MovieRepository
import com.vneuron.mvpkotlinexample.model.MovieTest
import com.vneuron.mvpkotlinexample.movies.MoviesActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),MainContract.ViewInterface {

    private lateinit var presenter: MainPresenter
    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        btnInsert.setOnClickListener {
            displayMessage("clicked")
            val title:String = edtTitle.text.toString()
            val desc:String = edtDesc.text.toString()
            if (title.isEmpty()){
                edtTitle.error = "insert a title"
                return@setOnClickListener
            }
            if (desc.isEmpty()){
                edtDesc.error = "insert a desc"
                return@setOnClickListener
            }
            val movie = MovieTest(title = title, description = desc, id = null)

            lifecycleScope.launch {
                presenter.insertMovie(movie)
            }
        }
        btnAllData.setOnClickListener {
            startActivity(Intent(this,MoviesActivity::class.java))
            finish()
        }
    }

    private fun setupPresenter(){
        movieRepository = MovieRepository(applicationContext)
        presenter = MainPresenter(this, movieRepository)
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
    companion object{
        private const val TAG = "MainActivity"
    }
}
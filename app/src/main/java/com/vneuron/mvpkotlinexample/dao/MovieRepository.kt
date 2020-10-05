package com.vneuron.mvpkotlinexample.dao

import android.content.Context
import android.util.Log
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.model.MovieTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(context: Context) {

    private var mDatabase:MovieDatabase = MovieDatabase.getInstance(context)

     suspend fun insertMovie(movieTest: MovieTest):Long = withContext(Dispatchers.IO){
            Log.d(TAG, "insertMovie: thread = ${Thread.currentThread().name} ")
            mDatabase.movieDao().insertMovie(movieTest)

    }
    suspend fun insertMovieToWishList(movie: Movie):Long = withContext(Dispatchers.IO){
        Log.d(TAG, "insertMovie: thread = ${Thread.currentThread().name} ")
        mDatabase.daoTest2().insertMovieToWishList(movie)

    }

    suspend fun getAllMovies():List<Movie> = withContext(Dispatchers.IO){
      return@withContext mDatabase.daoTest2().getAll()
    }

    suspend fun deleteAllMovies() = withContext(Dispatchers.IO){
        return@withContext mDatabase.movieDao().deleteAll()
    }


    companion object{
        private const val TAG = "MovieRepository"
    }
}
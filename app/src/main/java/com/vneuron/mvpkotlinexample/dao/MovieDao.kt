package com.vneuron.mvpkotlinexample.dao

import androidx.room.*
import com.vneuron.mvpkotlinexample.model.MovieTest

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun allMovies():List<MovieTest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieTest: MovieTest):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movieTest:MovieTest)

    @Delete
    fun deleteMovie(movieTest: MovieTest)

    @Query("DELETE FROM movies where id=:id")
    fun deleteMovieById(id:Int)

    @Query("DELETE FROM movies")
    fun deleteAll()

}
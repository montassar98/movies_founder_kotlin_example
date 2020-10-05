package com.vneuron.mvpkotlinexample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vneuron.mvpkotlinexample.model.Movie

@Dao
interface Test2Dao {

    @Query("SELECT * FROM  test2")
    fun getAll():List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieToWishList(movie: Movie):Long
}
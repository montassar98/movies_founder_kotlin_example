package com.vneuron.mvpkotlinexample.util

import com.vneuron.mvpkotlinexample.model.TmdbResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("search/movie")
    fun searchMovie(@Query("api_key") api_key:String, @Query("query") query: String) : Observable<TmdbResponse>

    @GET("search/movie")
    fun searchMovie(@Query("api_key") api_key:String, @Query("query") query: String,@Query("page") page:Int) : Observable<TmdbResponse>

}
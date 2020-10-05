package com.vneuron.mvpkotlinexample.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.util.RetrofitClient

class HomeRecyclerAdapter(private val context: Context,private val movies:MutableList<Movie>,private val listener:ItemClickListener): RecyclerView.Adapter<HomeRecyclerAdapter.MovieHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.item_movies_grid_view, parent, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.txtTitle.text = movies[position].title
        movies[position].posterPath?.run {
            Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + this).into(holder.imgPoster)
        }?: holder.imgPoster.setImageResource(R.drawable.img_empty)
        ViewCompat.setTransitionName(holder.itemView, "transition")
        holder.itemView.setOnClickListener { listener.onMovieClickedListener(holder.imgPoster,holder.adapterPosition, getItem(holder.adapterPosition)) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addItems(movies: MutableList<Movie>){
        this.movies.addAll(movies)
        Log.d(TAG, "addItems: ${this.movies.size}")
    }
    fun getItem(position: Int):Movie{
        return movies[position]
    }

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view){

        val imgPoster: ImageView = view.findViewById(R.id.img_poster)
        val txtTitle: TextView = view.findViewById(R.id.txt_title)

    }

    companion object{
        private const val TAG = "HomeRecyclerAdapter"

        interface ItemClickListener{
            fun onMovieClickedListener(sharedImageView: ImageView, position: Int, movie:Movie)
        }
    }
}
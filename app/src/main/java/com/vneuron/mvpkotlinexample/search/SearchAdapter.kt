package com.vneuron.mvpkotlinexample.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.model.Movie
import com.vneuron.mvpkotlinexample.util.RetrofitClient

class SearchAdapter(var context: Context, private var movieList: List<Movie>, var listener:ItemClickListener ):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_search,parent, false))
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.txtMovieTitle.text = movieList[position].title.toString()
        holder.txtMovieOverView.text = movieList[position].overview.toString()
        holder.imgMoviePoster.setImageResource(R.drawable.img_empty)
        movieList[position].posterPath?.let {
            Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + it).into(holder.imgMoviePoster)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }



    inner class SearchViewHolder(v: View):RecyclerView.ViewHolder(v){
        val txtMovieTitle: TextView = v.findViewById(R.id.txtMovieTitle)
        val txtMovieOverView: TextView = v.findViewById(R.id.txtMovieOverview)
        val imgMoviePoster: ImageView = v.findViewById(R.id.imgPoster)

        init {
            v.setOnClickListener {
               listener.onItemClickListener(it, adapterPosition)
            }
        }
    }

    interface ItemClickListener{
        fun onItemClickListener(view:View, position:Int)
    }
}
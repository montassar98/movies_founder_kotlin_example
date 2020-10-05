package com.vneuron.mvpkotlinexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.model.MovieTest
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val context:Context,private val dataList: List<MovieTest>):
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.item_movie,parent, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.txtId.text = dataList[position].id.toString()
        holder.txtTitle.text = dataList[position].title
        holder.txtDescription.text = dataList[position].description
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MovieHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtId:TextView = view.txtMovieId
        val txtTitle: TextView = view.txtMovieTitle
        val txtDescription:TextView = view.txtMovieDesc
    }
}
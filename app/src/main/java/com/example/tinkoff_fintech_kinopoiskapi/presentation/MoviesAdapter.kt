package com.example.tinkoff_fintech_kinopoiskapi.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.tinkoff_fintech_kinopoiskapi.R
import com.example.tinkoff_fintech_kinopoiskapi.domain.Movie

class MoviesAdapter : Adapter<MoviesAdapter.MoviesViewHolder>() {

    var movies: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onReachEndListener: OnReachEndListener? = null
        set(value) {
            field = value
        }
    var onMovieClickListener: OnMovieClickListener? = null
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false
        )
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies.get(position)
        Glide.with(holder.itemView)
            .load(movie.posterUrl)
            .into(holder.ivPosterRV)
        holder.tvTitleRV.text = movie.nameRu
        holder.tvGenreAndYear.text = movie.year

        if (position >= movies.size - 10 && onReachEndListener != null) {
            onReachEndListener?.onReachEnd()
        }

        holder.itemView.setOnClickListener {
            onMovieClickListener?.onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvTitleRV: TextView = itemView.findViewById(R.id.tvTitleRV)
        val tvGenreAndYear: TextView = itemView.findViewById(R.id.tvGenreAndYear)
        val ivPosterRV: ImageView = itemView.findViewById(R.id.ivPosterRV)

    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }
}
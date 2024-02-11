package com.example.tinkoff_fintech_kinopoiskapi.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoff_fintech_kinopoiskapi.R
import com.example.tinkoff_fintech_kinopoiskapi.domain.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvErrorMsg: TextView
    private lateinit var btnRetry : Button
    private lateinit var ivError: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()



        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        viewModel.movies.observe(this) {
                moviesAdapter.movies = it

        }

        btnRetry.setOnClickListener {
            viewModel.loadMovies()
        }

        viewModel.errorMessage.observe(this) {
            if (it) {
                tvErrorMsg.visibility = View.VISIBLE
                btnRetry.visibility = View.VISIBLE
                ivError.visibility = View.VISIBLE
                if (recyclerView.visibility == View.VISIBLE) {
                    recyclerView.visibility = View.GONE
                }

            } else {
                tvErrorMsg.visibility = View.GONE
                btnRetry.visibility = View.GONE
                ivError.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }



        moviesAdapter.onReachEndListener = (object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })

        moviesAdapter.onMovieClickListener = (object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@MainActivity, movie)
                startActivity(intent)
            }
        })

    }


    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerViewMovies)
        tvErrorMsg = findViewById(R.id.tvErrorMsg)
        btnRetry = findViewById(R.id.buttonRetry)
        ivError = findViewById(R.id.errorIcon)
    }
}
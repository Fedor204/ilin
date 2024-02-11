package com.example.tinkoff_fintech_kinopoiskapi.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tinkoff_fintech_kinopoiskapi.R
import com.example.tinkoff_fintech_kinopoiskapi.domain.Movie

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvCountries: TextView
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var ivArrowBack: ImageView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViews()

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        val movie = intent.getParcelableExtra(EXTRA_MOVIE_DETAIL, Movie::class.java)

        Glide.with(this)
            .load(movie?.posterUrl)
            .into(poster)
        movie?.let {
            tvTitle.text = it.nameRu
            tvGenre.text = it.parseGenre()
            tvCountries.text = it.parseCountries()
        }

        movie?.filmId?.let { viewModel.loadDescription(it) }

        viewModel.description.observe(this, Observer {
            tvDescription.text = it.description
        })

        ivArrowBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

    }



    private fun initViews() {
        poster = findViewById(R.id.imageViewPoster)
        tvTitle = findViewById(R.id.tvTitle)
        tvDescription = findViewById(R.id.tvDescription)
        tvGenre = findViewById(R.id.tvGenres)
        tvCountries = findViewById(R.id.tvCountries)
        ivArrowBack = findViewById(R.id.ivArrowBack)
    }


    companion object {

        private const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"

        fun newIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
            return intent
        }
    }
}
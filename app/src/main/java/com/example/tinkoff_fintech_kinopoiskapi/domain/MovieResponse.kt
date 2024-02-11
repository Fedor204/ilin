package com.example.tinkoff_fintech_kinopoiskapi.domain

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("pagesCount") val pagesCount: Int,
    @SerializedName("films") val films: List<Movie>
)
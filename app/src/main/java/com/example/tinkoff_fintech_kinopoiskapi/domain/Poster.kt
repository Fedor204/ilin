package com.example.tinkoff_fintech_kinopoiskapi.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @SerializedName("posterUrl") private val url: String,
) : Serializable
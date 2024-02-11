package com.example.tinkoff_fintech_kinopoiskapi.domain

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favourite_movies")
@Parcelize
data class Movie(
    @SerializedName("filmId") val filmId: Int,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("year") val year: String,
    @SerializedName("countries") val countries: List<Country>,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("description") val description: String?
) : Parcelable{

    fun parseGenre(): String {
        val genresStrings = genres.map { it.genre }
        return "Жанры: ${genresStrings.joinToString(", ")}"
    }
    fun parseCountries(): String {
        val countries = countries.map { it.country }
        return "Страны: ${countries.joinToString(", ")}"
    }
}

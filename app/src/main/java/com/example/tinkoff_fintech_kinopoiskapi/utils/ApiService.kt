package com.example.tinkoff_fintech_kinopoiskapi.utils

import com.example.tinkoff_fintech_kinopoiskapi.domain.DescriptionResponse
import com.example.tinkoff_fintech_kinopoiskapi.domain.MovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    fun loadMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("api/v2.2/films/{idFilm}")
    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    fun loadDescription(@Path("idFilm") idFilm: Int): Observable<DescriptionResponse>

}
package com.example.tinkoff_fintech_kinopoiskapi.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    @SerializedName("country") val country: String
) : Parcelable
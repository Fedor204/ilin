package com.example.tinkoff_fintech_kinopoiskapi.domain

import com.google.gson.annotations.SerializedName

data class DescriptionResponse(
    @SerializedName("description") val description: String
)
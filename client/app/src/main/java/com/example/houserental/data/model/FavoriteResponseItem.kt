package com.example.houserental.data.model

data class FavoriteResponseItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val location: String,
    // add any other fields returned from the API
)

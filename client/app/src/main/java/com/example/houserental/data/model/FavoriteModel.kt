package com.example.houserental.data.model

data class FavoriteRequest(val user_id: Int, val listing_id: Int)
data class FavoriteResponse(val message: String)
data class MessageResponse(
    val message: String
)

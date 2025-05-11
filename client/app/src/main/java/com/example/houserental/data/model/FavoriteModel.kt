package com.example.houserental.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("listing_id") val listingId: Int
)

data class FavoriteResponse(
    val status: String,
    val favorites: List<HouseListing>
)
data class MessageResponse(
    val message: String
)

package com.example.houserental.data.repository

import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.FavoriteRequest

class FavoriteRepository(private val api: ApiService) {
    suspend fun addToFavorite(userId: Int, listingId: Int): Result<String> {
        return try {
            val response = api.addToFavorite(FavoriteRequest(userId, listingId))
            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Success")
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

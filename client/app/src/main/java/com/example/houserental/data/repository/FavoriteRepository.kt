package com.example.houserental.data.repository

import android.util.Log
import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.FavoriteRequest
import com.example.houserental.data.model.HouseListing

class FavoriteRepository(private val api: ApiService) {
    suspend fun addToFavorite(userId: Int, listingId: Int): Result<String> {
        return try {
            val response = api.addToFavorite(FavoriteRequest(userId, listingId))
            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Success")
            } else {
                val errorBody = response.errorBody()?.string()
                println("❌ Failed with code ${response.code()}, error: $errorBody")
                Result.failure(Exception("Error: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            println("❌ Exception: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    suspend fun getFavoriteHouses(userId: Int): List<HouseListing> {
        val response = api.getFavorites(userId)
        Log.d("FavoriteViewModel", "Fetched favorites response: $response")
        return response.favorites
    }


}

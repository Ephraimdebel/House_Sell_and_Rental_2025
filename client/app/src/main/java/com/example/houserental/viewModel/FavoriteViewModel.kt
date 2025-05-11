package com.example.houserental.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    fun addToFavorite(userId: Int, listingId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.addToFavorite(userId, listingId)
            onResult(result.isSuccess)
        }
    }
    fun removeFromFavorite(userId: Int, listingId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.removeFromFavorite(userId, listingId)
            onResult(result.isSuccess)
        }
    }



    private val _favoriteListings = MutableStateFlow<List<HouseListing>>(emptyList())
    val favoriteListings: StateFlow<List<HouseListing>> = _favoriteListings

    fun getFavorites(userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getFavoriteHouses(userId)
                Log.d("FavoriteViewModel", "Fetched favorites: $response")
                _favoriteListings.value = response
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error fetching favorites: ${e.message}")
            }
        }
    }
    fun isHouseFavorited(houseId: Int): Boolean {
        return _favoriteListings.value.any { it.id == houseId }
    }
    private val _favoriteHouseIds = MutableStateFlow<List<Int>>(emptyList())
    val favoriteHouseIds: StateFlow<List<Int>> = _favoriteHouseIds
    




}

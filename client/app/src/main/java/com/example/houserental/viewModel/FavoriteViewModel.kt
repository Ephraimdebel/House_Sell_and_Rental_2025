package com.example.houserental.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    fun addToFavorite(userId: Int, listingId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.addToFavorite(userId, listingId)
            onResult(result.isSuccess)
        }
    }
}

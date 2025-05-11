package com.example.houserental.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.repository.HomeRepository
import kotlinx.coroutines.launch

class ManageHomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    var listings by mutableStateOf<List<HouseListing>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        getHomes()
    }

    fun getHomes() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.fetchAll()
            isLoading = false

            result
                .onSuccess { listings = it.data }
                .onFailure { errorMessage = it.message }
        }
    }

    fun deleteHouse(id: Int) {
        viewModelScope.launch {
            isLoading = true
            val result = repository.deleteHouse(id)
            isLoading = false
            result.onSuccess {
                listings = listings.filterNot { it.id == id }
            }.onFailure {
                errorMessage = it.message
            }
        }
    }
}


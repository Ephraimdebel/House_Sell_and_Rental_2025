package com.example.houserental.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    var listings by mutableStateOf<List<HouseListing>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var currentPage = 1
    private val pageSize = 3
    private val currentTypeId = 1
    private var isLastPage = false

    init {
        getHomes()
    }

    fun getHomes(typeId: Int = currentTypeId, page: Int = currentPage, limit: Int = pageSize) {
        // Prevent unnecessary calls once all pages are loaded
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.fetchHomes(typeId, page, limit)
            isLoading = false

            result
                .onSuccess { response ->
                    val newData = response.data
                    if (newData.isNotEmpty()) {
                        // Avoid duplicates
                        listings = (listings + newData).distinctBy { it.id }
                        currentPage += 1
                    } else {
                        // If empty, it's the last page
                        isLastPage = true
                    }
                }
                .onFailure {
                    errorMessage = it.message
                }
        }
    }

    fun resetListings() {
        listings = emptyList()
        currentPage = 1
        isLastPage = false
    }
}

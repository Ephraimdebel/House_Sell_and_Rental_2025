package com.example.houserental.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    var searchResults = mutableStateOf<List<HouseListing>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun searchHomes(city: String, minPrice: Int?, maxPrice: Int?, typeId: Int? = null) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            val result = repository.searchHomes(city, minPrice, maxPrice, typeId)
            isLoading.value = false

            result
                .onSuccess { response -> searchResults.value = response }
                .onFailure { errorMessage.value = it.message }
        }
    }
}

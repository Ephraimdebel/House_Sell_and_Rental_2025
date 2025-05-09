package com.example.houserental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.data.model.House
import com.example.houserental.data.model.HouseListing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class HouseDetailViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    // House details state (nullable to handle loading state)
    private val _house = MutableStateFlow<House?>(null)
    val house: StateFlow<House?> = _house

    // Amenities state
    val amenities = MutableStateFlow<List<Int>>(emptyList())

    // Loading and Error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Load house details by ID
    fun loadHouseDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getPropertyById(id)

                if (result.isSuccess) {
                    val house = result.getOrNull()
                    _house.value = house // Assign directly to _house
                    // Handle amenities if necessary, or fetch amenities here if needed
                } else {
                    _error.value = "Error: ${result.exceptionOrNull()?.localizedMessage}"
                }

            } catch (e: Exception) {
                _error.value = "Error fetching house details"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

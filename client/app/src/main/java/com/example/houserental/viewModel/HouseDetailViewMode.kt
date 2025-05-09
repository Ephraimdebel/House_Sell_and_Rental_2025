package com.example.houserental.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.data.model.House
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
                // Fetch the house details from the repository
                val result = repository.getHouseDetail(id)

                // Set the house details if available
                _house.value = result

                // Parse the amenities (facilities) if they exist
                result?.facilities?.let {
                    try {
                        val jsonArray = JSONArray(it) // Parse stringified JSON array
                        val list = mutableListOf<Int>()
                        for (i in 0 until jsonArray.length()) {
                            list.add(jsonArray.getInt(i))
                        }
                        amenities.value = list // Store parsed amenities list
                    } catch (e: Exception) {
                        e.printStackTrace() // Handle parse errors gracefully
                    }
                }

            } catch (e: Exception) {
                // Handle any network or repository errors
                _error.value = "Error fetching house details"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

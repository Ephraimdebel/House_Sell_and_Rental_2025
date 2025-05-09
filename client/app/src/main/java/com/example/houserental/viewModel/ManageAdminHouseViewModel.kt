package com.example.houserental.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log
class DashboardViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _totalProperties = MutableStateFlow(0)
    val totalProperties: StateFlow<Int> = _totalProperties

    private val _propertiesForSale = MutableStateFlow(0)
    val propertiesForSale: StateFlow<Int> = _propertiesForSale

    private val _propertiesForRent = MutableStateFlow(0)
    val propertiesForRent: StateFlow<Int> = _propertiesForRent

    private val _totalSaleValue = MutableStateFlow(0.0)
    val totalSaleValue: StateFlow<Double> = _totalSaleValue

    private val _monthlyRentalIncome = MutableStateFlow(0.0)
    val monthlyRentalIncome: StateFlow<Double> = _monthlyRentalIncome

    // Load total properties count
    fun loadTotalProperties(typeId: Int) {
        viewModelScope.launch {
            try {
                // Unpack Result<List<HouseListing>> properly
                val result = repository.getHousesByType(typeId)
                if (result.isSuccess) {
                    val houses = result.getOrNull() ?: emptyList()
                    _totalProperties.value = houses.size // Ensure houses is a list
                    Log.d("Total Properties", "Total properties count: ${_totalProperties.value}")
                } else {
                    Log.e("API Error", "Failed to fetch houses: ${result.exceptionOrNull()?.message}")
                    _totalProperties.value = 0
                }
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch houses: ${e.message}")
                _totalProperties.value = 0
            }
        }
    }

    // Load properties for sale
    fun loadPropertiesForSale() {
        viewModelScope.launch {
            try {
                // Unpack Result<List<HouseListing>> properly
                val result = repository.getHousesByType(1)
                if (result.isSuccess) {
                    val housesForSale = result.getOrNull() ?: emptyList()
                    _propertiesForSale.value = housesForSale.size

                    // Sum the sale values after converting price to Double
                    val totalValue = housesForSale.sumOf { house ->
                        house.price.toDoubleOrNull() ?: 0.0
                    }
                    _totalSaleValue.value = totalValue
                } else {
                    Log.e("API Error", "Failed to fetch properties for sale: ${result.exceptionOrNull()?.message}")
                    _propertiesForSale.value = 0
                    _totalSaleValue.value = 0.0
                }
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch properties for sale: ${e.message}")
                _propertiesForSale.value = 0
                _totalSaleValue.value = 0.0
            }
        }
    }

    // Load properties for rent
    fun loadPropertiesForRent() {
        viewModelScope.launch {
            try {
                // Unpack Result<List<HouseListing>> properly
                val result = repository.getHousesByType(2)
                if (result.isSuccess) {
                    val housesForRent = result.getOrNull() ?: emptyList()
                    _propertiesForRent.value = housesForRent.size

                    // Sum the rental values after converting price to Double
                    val totalRent = housesForRent.sumOf { house ->
                        house.price.toDoubleOrNull() ?: 0.0
                    }
                    _monthlyRentalIncome.value = totalRent
                } else {
                    Log.e("API Error", "Failed to fetch properties for rent: ${result.exceptionOrNull()?.message}")
                    _propertiesForRent.value = 0
                    _monthlyRentalIncome.value = 0.0
                }
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch properties for rent: ${e.message}")
                _propertiesForRent.value = 0
                _monthlyRentalIncome.value = 0.0
            }
        }
    }
}

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

    fun loadTotalProperties(typeId: Int) {
        viewModelScope.launch {
            try {
                val houses = repository.getHousesByType(typeId)
                Log.d("API Response", "Houses fetched: $houses")
                _totalProperties.value = houses.size // Update the state with the size of the list
                Log.d("Total Properties", "Total properties count: ${_totalProperties.value}")
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch houses: ${e.message}")
                _totalProperties.value = 0 // In case of an error, set the count to 0
            }
        }
    }
    fun loadPropertiesForSale() {
        viewModelScope.launch {
            try {
                val housesForSale = repository.getHousesByType(1)
                _propertiesForSale.value = housesForSale.size

                val totalValue = housesForSale.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
                _totalSaleValue.value = totalValue
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch properties for sale: ${e.message}")
                _propertiesForSale.value = 0
                _totalSaleValue.value = 0.0
            }
        }
    }

    fun loadPropertiesForRent() {
        viewModelScope.launch {
            try {
                val housesForRent = repository.getHousesByType(2)
                _propertiesForRent.value = housesForRent.size

                val totalRent = housesForRent.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
                _monthlyRentalIncome.value = totalRent
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch properties for rent: ${e.message}")
                _propertiesForRent.value = 0
                _monthlyRentalIncome.value = 0.0
            }
        }
    }

    private val _totalSaleValue = MutableStateFlow(0.0)
    val totalSaleValue: StateFlow<Double> = _totalSaleValue

    private val _monthlyRentalIncome = MutableStateFlow(0.0)
    val monthlyRentalIncome: StateFlow<Double> = _monthlyRentalIncome

}


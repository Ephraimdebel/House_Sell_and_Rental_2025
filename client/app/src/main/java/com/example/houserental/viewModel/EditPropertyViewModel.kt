package com.example.houserental.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.UpdatePropertyRequest
import com.example.houserental.data.repository.HomeRepository

import com.example.houserental.utils.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class EditPropertyViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var price by mutableStateOf("")
    var area by mutableStateOf("")
    var bedroomCount by mutableStateOf("")
    var bathroomCount by mutableStateOf("")
    var streetAddress by mutableStateOf("")
    var city by mutableStateOf("")
    var state by mutableStateOf("")
    var propertyType by mutableStateOf("House") // Start with a default value
    var listingType by mutableStateOf("For Sale") // Start with a default value
    var zipCode by mutableStateOf("") // Default value as an empty string

    var facilities by mutableStateOf("")
    var photos by mutableStateOf<List<File>>(emptyList())

    var isLoading by mutableStateOf(false)
    var message by mutableStateOf<String?>(null)

    private var selectedImageUris by mutableStateOf<List<Uri>>(emptyList())

    fun setSelectedImages(uris: List<Uri>) {
        selectedImageUris = uris
    }

    fun toggleFacility(id: Int) {
        val currentFacilities = facilities
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
            ?.toMutableList() ?: mutableListOf()

        if (currentFacilities.contains(id)) {
            currentFacilities.remove(id)
        } else {
            currentFacilities.add(id)
        }

        facilities = currentFacilities.joinToString(",")
    }


    // Load existing property data
    fun loadPropertyById(propertyId: Int, context: Context) {
        viewModelScope.launch {
            try {
                val result = repository.getPropertyById(propertyId)
                if (result.isSuccess) {
                    val propertyResponse = result.getOrNull()
                    val property = propertyResponse // Get the first HouseListing

                    if (property != null) {
                        title = property.title
                        description = property.description
                        propertyType = property.type_id.toString()
                        listingType = property.category_id.toString()
                        price = property.price
                        bedroomCount = property.bedroomCount.toString()
                        bathroomCount = property.bathroomCount.toString()
                        area = property.area.toString()
                        streetAddress = property.streetAddress
                        city = property.city
                        state = property.province
                        facilities = property.facilities ?: ""
                        zipCode = ""

                        selectedImageUris = property.listingPhotoPaths.map {
                            Uri.parse(it.replace("http://localhost", "http://10.0.2.2"))
                        }
                    } else {
                        message = "Property not found"
                    }
                } else {
                    message = "Failed to load property: ${result.exceptionOrNull()?.localizedMessage}"
                }
            } catch (e: Exception) {
                Log.e("EditPropertyViewModel", "Error loading property", e)
                message = "Error loading property: ${e.localizedMessage}"
            }
        }
    }



    // Update the property on the server
    fun updateProperty(propertyId: Int) {
        viewModelScope.launch {
            try {
                val request = UpdatePropertyRequest(
                    category_id = listingType,
                    type_id = propertyType,
                    streetAddress = streetAddress,
                    city = city,
                    province = state,
                    bedroomCount = bedroomCount.toIntOrNull() ?: 0,
                    area = area.toIntOrNull() ?: 0,
                    bathroomCount = bathroomCount.toIntOrNull() ?: 0,
                    title = title,
                    description = description,
                    facilities = facilities.toString(),
                    price = price,

                )

                val result = repository.updateProperty(propertyId, request)

                if (result.isSuccess) {
                    Log.d("EditPropertyViewModel", "Property updated successfully")
                    message = "Property updated successfully"
                } else {
                    message = result.exceptionOrNull()?.localizedMessage ?: "Failed to update property"
                    Log.e("EditPropertyViewModel", message!!)
                }
            } catch (e: Exception) {
                Log.e("EditPropertyViewModel", "Error: ${e.localizedMessage}")
                message = "Error: ${e.localizedMessage}"
            }
        }
    }


}

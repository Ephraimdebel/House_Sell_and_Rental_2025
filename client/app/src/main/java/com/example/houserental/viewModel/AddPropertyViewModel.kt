package com.example.houserental.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddPropertyViewModel(private val repository: HomeRepository) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var propertyType by mutableStateOf("House")
    var listingType by mutableStateOf("For Sale")
    var price by mutableStateOf("")
    var bedroomCount by mutableStateOf("")
    var bathroomCount by mutableStateOf("")
    var area by mutableStateOf("")
//    var streetAddress by mutableStateOf("")
    var city by mutableStateOf("")
    var state by mutableStateOf("")
    var zipCode by mutableStateOf("")
    var country by mutableStateOf("Ethiopia")
    var facilities by mutableStateOf<List<Int>>(emptyList())
    var photos by mutableStateOf<List<File>>(emptyList())

    var category by mutableStateOf("")      // ← Add this
    var type by mutableStateOf("")          // ← Already you have: propertyType
    var streetAddress by mutableStateOf("") // ← You can combine street/city/state if needed
    var province by mutableStateOf("")


    var isLoading by mutableStateOf(false)
    var message by mutableStateOf<String?>(null)

    private var selectedImageUris by mutableStateOf<List<Uri>>(emptyList())

    fun setSelectedImages(uris: List<Uri>) {
        selectedImageUris = uris
    }

    fun toggleFacility(id: Int) {
        facilities = if (facilities.contains(id)) facilities - id else facilities + id
    }

    fun submitProperty(context: Context) {
        viewModelScope.launch {
            try {
                val imageParts = selectedImageUris.map { uri ->
                    val file = uriToFile(context, uri)
                    val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                    val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("photos", file.name, requestFile)
                }

                val response = repository.addProperty(
                    photos = imageParts,
                    title = title.toRequestBody("text/plain".toMediaType()),
                    price = price.toRequestBody("text/plain".toMediaType()),
                    description = description.toRequestBody("text/plain".toMediaType()),
                    bathroomCount = bathroomCount.toRequestBody("text/plain".toMediaType()),
                    bedroomCount = bedroomCount.toRequestBody("text/plain".toMediaType()),
                    country = country.toRequestBody("text/plain".toMediaType()),
                    area = area.toRequestBody("text/plain".toMediaType()),
                    facilities = facilities.joinToString(",").toRequestBody("text/plain".toMediaType()),

                    // NEW FIELDS (make sure your API matches these)
                    category = propertyType.toRequestBody("text/plain".toMediaType()), // "House", etc.
                    type = listingType.toRequestBody("text/plain".toMediaType()),     // "For Sale", etc.

                    streetAddress = streetAddress.toRequestBody("text/plain".toMediaType()),
                    city = city.toRequestBody("text/plain".toMediaType()),
                    province = state.toRequestBody("text/plain".toMediaType()),


                )


                // Handle response
            } catch (e: Exception) {
                Log.e("AddPropertyViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

}

package com.example.houserental.data.repository

import AddPropertyRequest
import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.model.UpdatePropertyRequest
import com.example.houserental.data.model.HouseListing
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.network.RetrofitInstance.api
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
//import okhttp3.Response

import retrofit2.Response


class HomeRepository(private val apiService: ApiService) {

   suspend fun fetchHomes(typeId: Int, page: Int, limit: Int) =
       try {
           val response = apiService.getHousesByType(typeId, page, limit)
           if (response.isSuccessful && response.body() != null) {
               Result.success(response.body()!!)
           } else {
               Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
           }
       } catch (e: Exception) {
           Result.failure(e)
       }
   suspend fun deleteHouse(id: Int): Result<Unit> {
           return try {
               val response = RetrofitInstance.api.deleteHouse(id)
               if (response.isSuccessful) Result.success(Unit)
               else Result.failure(Exception("Failed to delete"))
           } catch (e: Exception) {
               Result.failure(e)
           }
       }
    suspend fun getHousesByType(typeId: Int): List<HouseListing> {

        return apiService.getHousesByType(typeId).data
    }


    suspend fun addProperty(
        photos: List<MultipartBody.Part>,
        title: RequestBody,
        price: RequestBody,
        description: RequestBody,
        bathroomCount: RequestBody,
        bedroomCount: RequestBody,
        country: RequestBody,
        area: RequestBody,
        facilities: RequestBody,
        category: RequestBody,     // ← ADD THIS
        type: RequestBody,         // ← ADD THIS
        streetAddress: RequestBody,       // ← ADD THIS
        city: RequestBody,         // ← ADD THIS
        province: RequestBody      // ← ADD THIS
    ): Response<Unit> {
        return apiService.addHouse(
            photos = photos,
            title = title,
            price = price,
            description = description,
            bathroomCount = bathroomCount,
            bedroomCount = bedroomCount,
            country = country,
            area = area,
            facilities = facilities,
            category = category,
            type = type,
            streetAddress = streetAddress,
            city = city,
            province = province
        )
    }

    // Function to fetch a property by its ID
    suspend fun getPropertyById(id: Int): HouseListing {
        val response = apiService.getPropertyById(id)
        if (response.isSuccessful) {
            val data = response.body()?.data?.firstOrNull()
            return data ?: throw Exception("No property found")
        } else {
            throw Exception("Failed to load property: ${response.message()}")
        }
    }

    suspend fun updateProperty(id: Int, updatedProperty: UpdatePropertyRequest): Result<HouseListing> {
        return try {
            val response = apiService.updateProperty(id, updatedProperty)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Update failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }








}

package com.example.houserental.data.repository

import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.HouseDetailResponse
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.model.ListingResponse
import com.example.houserental.data.model.PropertyResponse
import com.example.houserental.data.model.UpdatePropertyRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class HomeRepository(private val apiService: ApiService) {

    suspend fun fetchHomes(typeId: Int, page: Int, limit: Int): Result<ListingResponse> {
        return try {
            val response = apiService.getHousesByType(typeId, page, limit)
            if (response.isSuccessful) {
                Result.success(response.body() ?: ListingResponse("", emptyList(), 0, 0, 0))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteHouse(id: Int): Result<Unit> {
        return try {
            val response = apiService.deleteHouse(id)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Delete failed: ${response.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHousesByType(typeId: Int): Result<List<HouseListing>> {
        return try {
            val response = apiService.getHousesByType(typeId)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        category: RequestBody,
        type: RequestBody,
        streetAddress: RequestBody,
        city: RequestBody,
        province: RequestBody
    ): Result<Unit> {
        return try {
            val response = apiService.addHouse(
                photos,
                title,
                price,
                description,
                bathroomCount,
                bedroomCount,
                country,
                area,
                facilities,
                category,
                type,
                streetAddress,
                city,
                province
            )
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Add property failed: ${response.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPropertyById(id: Int): Result<PropertyResponse> {
        return try {
            val response = apiService.getPropertyById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get property failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProperty(id: Int, updatedProperty: UpdatePropertyRequest): Result<HouseListing> {
        return try {
            val response = apiService.updateProperty(id, updatedProperty)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Update failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchHomes(
        city: String,
        minPrice: Int?,
        maxPrice: Int?,
        typeId: Int?
    ): Result<List<HouseListing>> {
        return try {
            val response = apiService.getHousesByLocation(city, typeId, minPrice, maxPrice)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Search failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

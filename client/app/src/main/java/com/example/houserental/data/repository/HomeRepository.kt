package com.example.houserental.data.repository

import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.House
import com.example.houserental.data.model.HouseDetailResponse
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.model.ListingResponse
import com.example.houserental.data.model.PropertyResponse
import com.example.houserental.data.model.UpdatePropertyRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
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

    suspend fun getPropertyById(id: Int): Result<House> {
        return try {
            // Make the API call and get the response
            val response = apiService.getPropertyById(id)

            // Check if the response is successful and contains a valid body
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!

                // Try to fetch the first house from the data list
                val house = body.data.firstOrNull()

                // If house is found, return it as a success result
                if (house != null) {
                    Result.success(house)
                } else {
                    // If no house is found, return failure with an appropriate message
                    Result.failure(Exception("No property found"))
                }
            } else {
                // Return failure if the response was not successful
                Result.failure(Exception("Get property failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            // Return failure in case of an exception
            Result.failure(e)
        }
    }


    // Helper function to parse the facilities string into a List<Int>
    private fun parseFacilities(facilitiesString: String?): List<Int> {
        return if (!facilitiesString.isNullOrEmpty()) {
            try {
                val jsonArray = JSONArray(facilitiesString)
                val list = mutableListOf<Int>()
                for (i in 0 until jsonArray.length()) {
                    list.add(jsonArray.getInt(i))
                }
                list
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList() // Return an empty list if parsing fails
            }
        } else {
            emptyList() // Return an empty list if facilitiesString is null or empty
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

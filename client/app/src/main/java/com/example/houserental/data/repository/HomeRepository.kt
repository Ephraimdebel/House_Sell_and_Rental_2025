//package com.example.houserental.data.repository
//
//import com.example.houserental.data.api.ApiService
//import com.example.houserental.network.RetrofitInstance
//
//class HomeRepository(private val apiService: ApiService) {
//
//    suspend fun fetchHomes(typeId: Int, page: Int, limit: Int) =
//        try {
//            val response = apiService.getHousesByType(typeId, page, limit)
//            if (response.isSuccessful && response.body() != null) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    suspend fun deleteHouse(id: Int): Result<Unit> {
//            return try {
//                val response = RetrofitInstance.api.deleteHouse(id)
//                if (response.isSuccessful) Result.success(Unit)
//                else Result.failure(Exception("Failed to delete"))
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//
//}

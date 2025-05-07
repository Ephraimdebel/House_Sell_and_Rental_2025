package com.example.houserental.data.repository

import com.example.houserental.data.api.ApiService
import com.example.houserental.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository(private val api: ApiService) {
    suspend fun fetchAllUsers(): Result<UserResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getAllUsers()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun deleteUser(id: Int): Boolean {
        return try {
            val response = api.deleteUser(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

}

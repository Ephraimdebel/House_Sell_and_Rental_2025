package com.example.houserental.data.repository

import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import com.example.houserental.data.api.ApiService
import org.json.JSONObject
import retrofit2.HttpException

class AuthRepository(
    private val api: ApiService
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.loginUser(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(
        fullName: String,
        email: String,
        password: String,
        phone: String
    ): Result<RegisterResponse> {
        return try {
            val response = api.registerUser(
                RegisterRequest(
                    full_name = fullName,
                    email = email,
                    password = password,
                    phone_number = phone
                )
            )

            // Assuming your API wraps successful responses directly
            Result.success(response)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorBody).getString("message")
            } catch (jsonException: Exception) {
                "Registration failed"
            }
            Result.failure(Exception(errorMessage))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}

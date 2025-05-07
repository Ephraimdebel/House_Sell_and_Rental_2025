package com.example.houserental.data.api

import com.example.houserental.data.model.ListingResponse
import com.example.houserental.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {
    @GET("api/housetype")
    suspend fun getHousesByType(
        @Query("type_id") typeId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ListingResponse>
    @DELETE("api/house/{id}")
    suspend fun deleteHouse(@Path("id") id: Int): Response<Unit>
    @GET("api/users/getAllUsers")
    suspend fun getAllUsers(): Response<UserResponse>
    @DELETE("api/users/user/{id}")
    suspend fun deleteUser(@Path("id") id: Int): retrofit2.Response<Unit>

    @GET("api/housetype")
    suspend fun getHousesByType(@Query("type_id") typeId: Int): ListingResponse

}

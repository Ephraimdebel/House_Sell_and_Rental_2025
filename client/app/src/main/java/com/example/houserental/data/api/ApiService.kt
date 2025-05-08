package com.example.houserental.data.api

import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import com.example.houserental.data.model.ListingResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    @POST("api/users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/users/register")
    suspend fun registerUser(
        @Body user: RegisterRequest
    ): RegisterResponse



//    @Multipart
//    @POST("/api/addHouse")
//    suspend fun addProperty(
//        @Part photos: List<MultipartBody.Part>,
//        @Part("price") price: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("title") title: RequestBody,
//        @Part("bathroomCount") bathroomCount: RequestBody,
//        @Part("bedroomCount") bedroomCount: RequestBody,
//        @Part("country") country: RequestBody,
//        @Part("area") area: RequestBody,
//        @Part("facilities") facilities: RequestBody
//    ): Response<Unit>

    @Multipart
    @POST("api/addHouse")
    suspend fun addHouse(
        @Part photos: List<MultipartBody.Part>,
        @Part("title") title: RequestBody,
        @Part("price") price: RequestBody,
        @Part("description") description: RequestBody,
        @Part("bathroomCount") bathroomCount: RequestBody,
        @Part("bedroomCount") bedroomCount: RequestBody,
        @Part("country") country: RequestBody,
        @Part("area") area: RequestBody,
        @Part("facilities") facilities: RequestBody,
        @Part("category") category: RequestBody,     // ← ADD
        @Part("type") type: RequestBody,             // ← ADD
        @Part("streetAddress") streetAddress: RequestBody,         // ← ADD
        @Part("city") city: RequestBody,             // ← ADD
        @Part("province") province: RequestBody      // ← ADD
    ): Response<Unit>



}

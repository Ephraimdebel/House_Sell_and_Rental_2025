package com.example.houserental.data.api


import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import com.example.houserental.data.model.HouseDetailResponse
import com.example.houserental.data.model.HouseListing
import com.example.houserental.data.model.ListingResponse
import com.example.houserental.data.model.PropertyResponse
import com.example.houserental.data.model.UpdatePropertyRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.example.houserental.data.model.UserResponse

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PATCH
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

    @GET("api/users/getAllUsers")
    suspend fun getAllUsers(): Response<UserResponse>
    @DELETE("api/users/user/{id}")
    suspend fun deleteUser(@Path("id") id: Int): retrofit2.Response<Unit>

    @GET("api/housetype")
    suspend fun getHousesByType(@Query("type_id") typeId: Int): ListingResponse
    @POST("api/users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/users/register")
    suspend fun registerUser(
        @Body user: RegisterRequest
    ): RegisterResponse

    @GET("house/{id}")
    suspend fun getHouseDetail(@Path("id") id: Int): Response<HouseDetailResponse>


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

    @GET("api/house/{id}")
    suspend fun getPropertyById(@Path("id") id: Int): Response<PropertyResponse>


    @PATCH("api/properties/{id}")
    suspend fun updateProperty(
        @Path("id") id: Int,
        @Body updatedProperty: UpdatePropertyRequest
    ): Response<HouseListing>



//    @Multipart
//    @POST("api/addHouse")
//    suspend fun addHouse(
//        @Part photos: List<MultipartBody.Part>,
//        @Part("title") title: RequestBody,
//        @Part("price") price: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("bathroomCount") bathroomCount: RequestBody,
//        @Part("bedroomCount") bedroomCount: RequestBody,
//        @Part("country") country: RequestBody,
//        @Part("area") area: RequestBody,
//        @Part("facilities") facilities: RequestBody,
//        @Part("category") category: RequestBody,     // ← ADD
//        @Part("type") type: RequestBody,             // ← ADD
//        @Part("streetAddress") streetAddress: RequestBody,         // ← ADD
//        @Part("city") city: RequestBody,             // ← ADD
//        @Part("province") province: RequestBody      // ← ADD
//    ): Response<Unit>

//    @GET("api/house/{id}")
//    suspend fun getPropertyById(@Path("id") id: Int): Response<PropertyResponse>
//
//
//    @PATCH("api/properties/{id}")
//    suspend fun updateProperty(
//        @Path("id") id: Int,
//        @Body updatedProperty: UpdatePropertyRequest
//    ): Response<HouseListing>






}

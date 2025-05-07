package com.example.houserental.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val users: List<User>
)

data class User(
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val role: String
)

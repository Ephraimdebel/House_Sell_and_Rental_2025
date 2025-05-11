package com.example.houserental.data.model

data class UpdatePropertyRequest(
    val category_id: Int,
    val type_id: Int,
    val streetAddress: String,
    val city: String,
    val province: String,
    val bedroomCount: Int,
    val area: Int,
    val bathroomCount: Int,
    val title: String,
    val description: String,
    val facilities: String?,
    val price: String

)

package com.example.houserental.data.model

data class UpdatePropertyRequest(
    val category_id: String,
    val type_id: String,
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

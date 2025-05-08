package com.example.houserental.data.model

data class HouseDetailResponse(
    val message: String,
    val data: List<House>
)

data class House(
    val id: Int,
    val creator_id: Int,
    val category_id: Int,
    val type_id: Int,
    val streetAddress: String,
    val city: String,
    val province: String,
    val country: String,
    val guestCount: Int,
    val bedroomCount: Int,
    val area: Int,
    val bathroomCount: Int,
    val title: String,
    val description: String,
    val listingPhotoPaths: List<String>,
    val facilities: String,
    val price: String,
    val isFeatured: Int,
    val createdAt: String,
    val updatedAt: String
)

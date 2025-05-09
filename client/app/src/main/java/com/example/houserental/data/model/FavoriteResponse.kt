// FavoriteResponse.kt

data class FavoriteResponse(
    val favorites: List<House>
)

data class House(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val listingPhotoPaths: List<String>
)

data class FavoriteResponseItem(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val listingPhotoPaths: List<String>,
    val streetAddress: String,
    val city: String,
    val country: String,
    val facilities: String,
    // ... and any other fields from the API
)

data class HouseListing(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val listingPhotoPaths: List<String>,
    val streetAddress: String,
    val city: String,
    val country: String,
    val facilities: String,
    // ... match whatever your UI needs
)
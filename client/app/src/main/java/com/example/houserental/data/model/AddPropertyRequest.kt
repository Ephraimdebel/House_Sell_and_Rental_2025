import java.io.File

data class AddPropertyRequest(
    val title: String,
    val description: String,
    val propertyType: String,
    val listingType: String,
    val price: Double,
    val bedroomCount: Int,
    val bathroomCount: Int,
    val area: Double,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
    val facilities: List<Int>,
    val photos: List<File>
)

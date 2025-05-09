import com.example.houserental.data.model.HouseListing

// Now use it in your SearchResponse or SearchRepository
data class SearchResponse(
    val houses: List<HouseListing>
)

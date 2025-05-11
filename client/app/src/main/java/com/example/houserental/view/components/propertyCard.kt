import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.houserental.ui.theme.BlackText
import com.example.houserental.ui.theme.BrandColor
import com.example.houserental.view.IconText
import com.example.houserental.viewModel.FavoriteViewModel

@Composable
fun PropertyCard(
    house: com.example.houserental.data.model.HouseListing,
    isHorizontal: Boolean,
    userId: Int,
    viewModel: FavoriteViewModel,
    onRemoveFavorite: () -> Unit,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var isFavorited by remember { mutableStateOf(false) }

    // Check if the house is already in favorites
    LaunchedEffect(house.id) {
        isFavorited = viewModel.isHouseFavorited(house.id)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = if (isHorizontal)
            Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clickable { onClick() }
                .background(Color.White)
        else
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .background(Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        if (isHorizontal) {
            Row {
                Box(modifier = Modifier.width(140.dp).fillMaxHeight()) {
                    if (house.listingPhotoPaths.isNotEmpty()) {
                        val imageUrl = house.listingPhotoPaths.first()
                            .replace("http://localhost:5500", "http://10.0.2.2:5500")
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // For Rent / Sale Label
                    Text(
                        text = if (house.type_id == 1) "For sale" else "For rent",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                            .background(
                                color = BrandColor,
                                shape = RoundedCornerShape(3.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    )

                    // Favorite Icon (on image)
                    IconButton(
                        onClick = {
                            if (isFavorited) {
                                // Remove from favorites
                                viewModel.removeFromFavorite(userId, house.id) { success ->
                                    if (success) {
                                        isFavorited = false
                                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                                        onRemoveFavorite() // ✅ update the UI list immediately
                                    } else {
                                        Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                // Add to favorites
                                viewModel.addToFavorite(userId, house.id) { success ->
                                    if (success) {
                                        isFavorited = true
                                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(Color.Gray.copy(alpha = 0.8f), shape = CircleShape)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("ETB ${house.price}", fontWeight = FontWeight.Bold, color = BlackText)
                    Text(house.title, maxLines = 1, color = BlackText)
                    Text("${house.city}, ${house.streetAddress}", fontSize = 12.sp, color = Color.Gray)

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconText(Icons.Default.Bed, "${house.bedroomCount}")
                        IconText(Icons.Default.Bathtub, "${house.bathroomCount}")
                        IconText(Icons.Default.SquareFoot, "${house.area}ft")
                    }
                }
            }
        } else {
            Column {
                Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    if (house.listingPhotoPaths.isNotEmpty()) {
                        val imageUrl = house.listingPhotoPaths.first()
                            .replace("http://localhost:5500", "http://10.0.2.2:5500")
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // For Rent / Sale Label
                    Text(
                        text = if (house.type_id == 1) "For sale" else "For rent",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .background(
                                color = BrandColor,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    )

                    // Favorite Icon (on image)
                    IconButton(
                        onClick = {
                            if (isFavorited) {
                                // Remove from favorites
                                viewModel.removeFromFavorite(userId, house.id) { success ->
                                    if (success) {
                                        isFavorited = false
                                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                // Add to favorites
                                viewModel.addToFavorite(userId, house.id) { success ->
                                    if (success) {
                                        isFavorited = true
                                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(Color.Gray.copy(alpha = 0.8f), shape = CircleShape)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    }
                }

                Column(modifier = Modifier.padding(6.dp)) {
                    Text("ETB ${house.price}", fontWeight = FontWeight.Bold, color = BlackText)
                    Text(house.title, maxLines = 1, color = BlackText)
                    Text("${house.city}, ${house.streetAddress}", fontSize = 12.sp, color = Color.Gray)

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconText(Icons.Default.Bed, "${house.bedroomCount}")
                        IconText(Icons.Default.Bathtub, "${house.bathroomCount}")
                        IconText(Icons.Default.SquareFoot, "${house.area}ft")
                    }
                }
            }
        }
    }
}


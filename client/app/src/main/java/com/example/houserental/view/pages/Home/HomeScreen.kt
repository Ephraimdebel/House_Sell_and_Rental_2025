package com.example.houserental.view

import LoginResponse
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.houserental.data.repository.FavoriteRepository
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.preferences.UserPreferences
import com.example.houserental.ui.theme.Background
import com.example.houserental.ui.theme.BlackText
import com.example.houserental.viewModel.FavoriteViewModel
import com.example.houserental.viewModel.FavoriteViewModelFactory
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.HomeViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { HomeViewModelFactory(repository) }
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val listings = viewModel.listings
    LaunchedEffect(Unit) {
        viewModel.getHomes()
    }
    val favoriteRepository = remember { FavoriteRepository(RetrofitInstance.api) }
    val favoriteViewModelFactory = remember { FavoriteViewModelFactory(favoriteRepository) }

    // ViewModel with factory
    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = favoriteViewModelFactory,
        viewModelStoreOwner = viewModelStoreOwner!!
    )

    val userDetails = UserPreferences.getUserDetails(context).collectAsState(initial = LoginResponse(msg = "",
        token = "",
        username = "",
        userid = -1, // Default user ID if no user is logged in
        role = "",
        email = ""))
    val userId = userDetails.value.userid
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Home", fontSize = 20.sp, color = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Background)
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Text("Hello there", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text("Find your perfect home", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader("Featured Properties") {
                viewModel.getHomes()
            }
            HorizontalListingSection(listings, userId,favoriteViewModel) { id ->
                navController.navigate("property_detail/$id")
            }

            SectionHeader("For Sale") {
                viewModel.getHomes()
            }

            SectionHeader("For Rent") {
                viewModel.getHomes()
            }
            VerticalListingSection(listings, userId, favoriteViewModel) { id ->
                navController.navigate("property_detail/$id")
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onViewAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold,color = BlackText)
        Text(
            "View All",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onViewAllClick() }
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HorizontalListingSection(
    houses: List<com.example.houserental.data.model.HouseListing>,
    userId: Int,
    favoriteViewModel: FavoriteViewModel,
    onHouseClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        houses.forEach { house ->
            PropertyCard(house, isHorizontal = false,userId,favoriteViewModel) {
                onHouseClick(house.id)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun VerticalListingSection(
    houses: List<com.example.houserental.data.model.HouseListing>,
    userId: Int,
    favoriteViewModel: FavoriteViewModel,
    onHouseClick: (Int) -> Unit
)
{
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        houses.forEach { house ->
            PropertyCard(house, isHorizontal = true,userId,favoriteViewModel) {
                onHouseClick(house.id)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun PropertyCard(
    house: com.example.houserental.data.model.HouseListing,
    isHorizontal: Boolean,
    userId: Int,
    viewModel: FavoriteViewModel,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var isFavorited by remember {
        mutableStateOf(viewModel.isHouseFavorited(house.id))
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
                            .replace("http://localhost:5500", "https://house-rental-backend-4vof.onrender.com")
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // For Rent / Sale Label
                    Text(
                        text = if (house.type_id == 1) "For Rent" else "For Sale",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                            .background(
                                color = if (house.type_id == 1) Color(0xFF2196F3) else Color(0xFF4CAF50),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )

                    // Favorite Icon (on image)
                    IconButton(
                        onClick = {
                            viewModel.addToFavorite(userId, house.id) { success ->
                                if (success) {
                                    isFavorited = true
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to add favorite", Toast.LENGTH_SHORT).show()
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
                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorited) Color.Red else Color.White
                        )
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
                            .replace("http://localhost:5500", "https://house-rental-backend-4vof.onrender.com")
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // For Rent / Sale Label
                    Text(
                        text = if (house.type_id == 1) "For Rent" else "For Sale",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .background(
                                color = if (house.type_id == 1) Color(0xFF2196F3) else Color(0xFF4CAF50),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )

                    // Favorite Icon (on image)
                    IconButton(
                            onClick = {
                            viewModel.addToFavorite(userId, house.id) { success ->
                                if (success) {
                                    isFavorited = true
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to add favorite", Toast.LENGTH_SHORT).show()
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
                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorited) Color.Red else Color.White
                        )
                    }
                }

                Column(modifier = Modifier.padding(6.dp)) {
                    Text("ETB ${house.price}", fontWeight = FontWeight.Bold,color = BlackText)
                    Text(house.title, maxLines = 1,color=BlackText)
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



@Composable
fun IconText(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 12.sp, color = Color.Gray)
    }
}

fun formatImageUrl(originalUrl: String): String {
    return originalUrl.replace("http://localhost:5500", "http://10.0.2.2:5500")
}

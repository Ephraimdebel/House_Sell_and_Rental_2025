package com.example.houserental.view

import LoginResponse
import PropertyCard
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
import com.example.houserental.ui.theme.BrandColor
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

            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader("For Sale") {
                viewModel.getHomes()
            }

            VerticalListingSection(listings, userId, favoriteViewModel,isForSale = true) { id ->
                navController.navigate("property_detail/$id")
            }
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader("For Rent") {
                viewModel.getHomes()
            }
            VerticalListingSection(listings, userId, favoriteViewModel, isForSale = false) { id ->
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
    isForSale: Boolean,
    onHouseClick: (Int) -> Unit,
) {
    val filteredHouses = if (isForSale) {
        houses.filter { it.type_id == 1 }
    } else {
        houses.filter { it.type_id == 2 }
    }

    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        filteredHouses.forEach { house ->
            PropertyCard(house, isHorizontal = true, userId, favoriteViewModel) {
                onHouseClick(house.id)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
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

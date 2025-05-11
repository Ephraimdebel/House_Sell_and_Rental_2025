package com.example.houserental.view.pages.Favorite

import LoginResponse
import PropertyCard
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.houserental.data.repository.FavoriteRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.preferences.UserPreferences
import com.example.houserental.ui.theme.Background
import com.example.houserental.ui.theme.BlackText
//import com.example.houserental.view.PropertyCard
import com.example.houserental.viewModel.FavoriteViewModel
import com.example.houserental.viewModel.FavoriteViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    // Initialize ViewModel
    val favoriteRepository = remember { FavoriteRepository(RetrofitInstance.api) }
    val favoriteViewModelFactory = remember { FavoriteViewModelFactory(favoriteRepository) }
    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = favoriteViewModelFactory,
        viewModelStoreOwner = viewModelStoreOwner!!
    )

    // Get logged-in user info
    val userDetails = UserPreferences.getUserDetails(context).collectAsState(
        initial = LoginResponse("", "", "", -1, "", "")
    )
    val userId = userDetails.value.userid

    // Fetch favorites
    LaunchedEffect(userId) {
        if (userId != -1) {
            favoriteViewModel.getFavorites(userId)
        }
    }

    val favoriteListings by favoriteViewModel.favoriteListings.collectAsState(initial = emptyList())

    // Function to remove favorite
    fun removeFavorite(houseId: Int) {
        favoriteViewModel.removeFromFavorite(userId, houseId) { success ->
            if (success) {
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Favorites", fontSize = 20.sp, color = Color.Black)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Favorites", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = BlackText)
            if (favoriteListings.isEmpty()) {
                Text("No favorite properties yet.", color = Color.Gray)
            } else {
                favoriteListings.forEach { house ->
                    PropertyCard(
                        house = house,
                        isHorizontal = false,
                        userId = userId,
                        viewModel = favoriteViewModel,
                        onRemoveFavorite = { removeFavorite(house.id) }
                    ) {
                        navController.navigate("property_detail/${house.id}")
                    }
                }
            }
        }
    }
}



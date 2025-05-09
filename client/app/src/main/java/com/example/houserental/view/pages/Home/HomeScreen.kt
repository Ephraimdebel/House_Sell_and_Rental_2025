package com.example.houserental.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.HomeViewModelFactory

@Composable
fun HomeScreen(navController: NavController) {
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { HomeViewModelFactory(repository) }
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val listings = viewModel.listings
    LaunchedEffect(Unit) {
        viewModel.getHomes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Hello there", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Find your perfect home", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Featured Properties") {
            viewModel.getHomes()
        }
        HorizontalListingSection(listings) { id ->
            navController.navigate("property_detail/$id")
        }

        SectionHeader("For Sale") {
            viewModel.getHomes()
        }
        VerticalListingSection(listings) { id ->
            navController.navigate("property_detail/$id")
        }

        SectionHeader("For Rent") {
            viewModel.getHomes()
        }
        VerticalListingSection(listings) { id ->
            navController.navigate("property_detail/$id")
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
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
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
    onHouseClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        houses.forEach { house ->
            PropertyCard(house, isHorizontal = true) {
                onHouseClick(house.id)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun VerticalListingSection(
    houses: List<com.example.houserental.data.model.HouseListing>,
    onHouseClick: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        houses.forEach { house ->
            PropertyCard(house, isHorizontal = false) {
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
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = if (isHorizontal)
            Modifier
                .width(250.dp)
                .clickable { onClick() }
        else
            Modifier
                .fillMaxWidth()
                .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            val imageUrl = house.listingPhotoPaths.firstOrNull()
            val newImage = formatImageUrl(imageUrl.toString())
            Log.d("image url", "$newImage")

            Box {
                Image(
                    painter = rememberAsyncImagePainter(newImage),
                    contentDescription = house.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                )

                IconButton(
                    onClick = { /* TODO: handle favorite toggle */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text("ETB ${house.price}", fontWeight = FontWeight.Bold)
                Text(house.title, maxLines = 1)
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

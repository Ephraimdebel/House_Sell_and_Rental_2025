package com.example.houserental.ui.pages.home_detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.houserental.R
import com.example.houserental.viewModel.HouseDetailViewModel
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.data.api.ApiService
import coil.compose.AsyncImage as AsyncImage1
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.Background
import com.example.houserental.viewModel.HouseDetailViewModelFactory
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailTopBar(navController: NavController) {
    TopAppBar(
        title = { Text("Property Detail") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
    )
}

@Composable
fun PropertyDetailScreen(houseId: Int, navController: NavController) {
    // Create the ViewModelFactory
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { HouseDetailViewModelFactory(repository) }
    val viewModel: HouseDetailViewModel = viewModel(factory = factory)

    LaunchedEffect(houseId) {
        viewModel.loadHouseDetail(houseId)
    }

    Scaffold(
        topBar = { PropertyDetailTopBar(navController) },
        containerColor = Background
    ) { paddingValues ->
        PropertyDetailPage(
            viewModel = viewModel,
            houseId = houseId,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun PropertyDetailPage(
    viewModel: HouseDetailViewModel,
    houseId: Int,
    modifier: Modifier = Modifier
) {
    val house by viewModel.house.collectAsState()
    val amenities by viewModel.amenities.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    if (isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    error?.let {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = it, color = Color.Red)
        }
        return
    }

    house?.let { houseData ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val firstImageUrl = houseData.listingPhotoPaths.firstOrNull()
                ?.replace("localhost", "10.0.2.2")

            firstImageUrl?.let { imageUrl ->
                AsyncImage1(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))  // Apply rounded corners here
//                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))  // Optional: Add a border
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "$${houseData.price}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = houseData.title, fontSize = 18.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(18.dp).padding(end = 4.dp)
                )
                Text(
                    text = "${houseData.streetAddress}, ${houseData.city}, ${houseData.country}",
                    fontSize = 14.sp,
                    color = Color(0xFF424242)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))  // Apply rounded corners here
//                    .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))  // Optional: Add a border
            ) {
                StatCard(
                    icon = Icons.Default.Bed,
                    label = "Beds",
                    value = houseData.bedroomCount.toString() ?: "-"
                )
                StatCard(
                    icon = Icons.Default.Bathtub,
                    label = "Baths",
                    value = houseData.bathroomCount?.toString() ?: "-"
                )
                StatCard(
                    icon = Icons.Default.SquareFoot,
                    label = "Sq Ft",
                    value = houseData.area?.toString() ?: "-"
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text("Description", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = houseData.description, modifier = Modifier.padding(vertical = 8.dp))

            Text("Amenities", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            AmenitiesSection(houseData.facilities.toIntList())

            Spacer(modifier = Modifier.height(16.dp))

            Text("Listed by", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avator),
                        contentDescription = "Agent Avatar",
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text("Teshome Toga", fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { /* Contact agent logic */ },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Contact", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Contact agent logic */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call Agent",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contact Agent", color = Color.White)
            }
        }
    }
}

@Composable
fun StatCard(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF2196F3),
            modifier = Modifier.size(24.dp)
        )
        Text(value, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp)
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun AmenitiesSection(amenityIds: List<Int>) {
    val allAmenities = mapOf(
        1 to Pair(Icons.Default.Wifi, "WiFi"),
        2 to Pair(Icons.Default.DirectionsCar, "Parking"),
        3 to Pair(Icons.Default.WaterDrop, "Swimming Pool"),
        4 to Pair(Icons.Default.FitnessCenter, "Gym"),
        5 to Pair(Icons.Default.Air, "Air Conditioning"),
        6 to Pair(Icons.Default.AcUnit, "Heating")
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // mainAxisSpacing equivalent
        verticalArrangement = Arrangement.spacedBy(8.dp)   // crossAxisSpacing equivalent
    ) {
        amenityIds.forEach { id ->
            allAmenities[id]?.let { (icon, label) ->
                OutlinedCard(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Background)  // Apply Background color here
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = label)
                    }
                }

            }
        }
    }
}


fun String?.toIntList(): List<Int> {
    return this?.split(",")
        ?.mapNotNull { it.trim().toIntOrNull() }
        ?: emptyList() // Return empty list if the string is null or empty
}

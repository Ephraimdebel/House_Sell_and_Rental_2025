package com.example.houserental.ui.pages.home_detail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.houserental.R
import com.example.houserental.viewModel.HouseDetailViewModel
import com.example.houserental.viewModel.HouseDetailViewModelFactory
import com.google.accompanist.flowlayout.FlowRow
//import kotlinx.coroutines.flow.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailTopBar(navController: NavController) {
    TopAppBar(
        title = { Text("Property Detail") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun PropertyDetailScreen(houseId: Int, navController: NavController) {
    val viewModel: HouseDetailViewModel = viewModel()

    // Load the house details
    LaunchedEffect(houseId) {
        viewModel.loadHouseDetail(houseId)
    }

    Scaffold(topBar = { PropertyDetailTopBar(navController) }, containerColor = Color.White) { paddingValues ->
        PropertyDetailPage(viewModel = viewModel, houseId = houseId, modifier = Modifier.padding(paddingValues))
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

    // Show loading indicator
    if (isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Show error message if any
    error?.let {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = it, color = Color.Red)
        }
        return
    }

    house?.let { houseData ->
        Column(modifier = modifier.padding(16.dp)) {
            val firstImageUrl = houseData.listingPhotoPaths.firstOrNull()?.replace("localhost", "10.0.2.2")

            firstImageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "$${houseData.price}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = houseData.title, fontSize = 18.sp)

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location", tint = Color(0xFF2196F3), modifier = Modifier.size(18.dp).padding(end = 4.dp))
                Text(text = "${houseData.streetAddress}, ${houseData.city}, ${houseData.country}", fontSize = 14.sp, color = Color(0xFF424242))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                StatCard(icon = Icons.Default.Bed, label = "Beds", value = "2")
                StatCard(icon = Icons.Default.Bathtub, label = "Baths", value = "2")
                StatCard(icon = Icons.Default.SquareFoot, label = "Sq Ft", value = "1200")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Description", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = houseData.description, modifier = Modifier.padding(vertical = 8.dp))

            Text("Amenities", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            AmenitiesSection(amenities)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Listed by", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Surface(shape = CircleShape, modifier = Modifier.size(48.dp)) {
                    Image(painter = painterResource(id = R.drawable.avator), contentDescription = "Agent Avatar", contentScale = ContentScale.Crop)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text("Sarah Johnson", fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.weight(1f)) // Pushes button to the right

                Button(onClick = { /* Contact agent logic */ }, shape = RoundedCornerShape(24.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Contact", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* Contact agent logic */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), contentPadding = PaddingValues(vertical = 12.dp)) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Call Agent", modifier = Modifier.size(18.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contact Agent", color = Color.White)
            }
        }
    }
}

@Composable
fun StatCard(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, tint = Color(0xFF2196F3), modifier = Modifier.size(24.dp))
        Text(value, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp)
    }
}

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

    FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp, modifier = Modifier.fillMaxWidth()) {
        amenityIds.forEach { id ->
            allAmenities[id]?.let { (icon, label) ->
                OutlinedCard(shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, Color.LightGray), modifier = Modifier.padding(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                        Icon(imageVector = icon, contentDescription = label, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = label)
                    }
                }
            }
        }
    }
}

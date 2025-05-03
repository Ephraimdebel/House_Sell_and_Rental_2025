//package com.example.houserental.view.pages.Manage_Home
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import com.example.houserental.data.model.HouseListing
//import com.example.houserental.data.repository.HomeRepository
//import com.example.houserental.network.RetrofitInstance
//import com.example.houserental.viewModel.ManageHomeViewModel
//import com.example.houserental.viewModel.ManageHomeViewModelFactory
////import androidx.compose.runtime.remember
//@Composable
//fun ManageHomeScreen() {
//    val repository = remember { HomeRepository(RetrofitInstance.api) }
//    val factory = remember { ManageHomeViewModelFactory(repository) }
//    val viewModel: ManageHomeViewModel = viewModel(factory = factory)
//
//    val listings = viewModel.listings
//    val isLoading = viewModel.isLoading
//    val error = viewModel.errorMessage
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("Manage Homes", style = MaterialTheme.typography.titleLarge)
//
//        when {
//            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//            error != null -> Text("Error: $error", color = Color.Red)
//            else -> {
//                LazyColumn {
//                    items(listings) { house ->
//                        HouseItem(house)
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HouseItem(house: HouseListing) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable { /* navigate to detail */ },
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column {
//            if (house.listingPhotoPaths.isNotEmpty()) {
//                AsyncImage(
//                    model = house.listingPhotoPaths.first(),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(house.title, fontWeight = FontWeight.Bold)
//                Text(house.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
//                Text("Price: \$${house.price}")
//            }
//        }
//    }
//}

package com.example.houserental.view.pages.manage_home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.houserental.R
import com.example.houserental.data.model.HouseListing
import com.example.houserental.viewModel.ManageHomeViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.viewModel.ManageHomeViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageHomeScreen() {
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { ManageHomeViewModelFactory(repository) }
    val viewModel: ManageHomeViewModel = viewModel(factory = factory)

    val listings = viewModel.listings
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Manage Properties") },

                navigationIcon = {
                    IconButton(onClick = { /* navigate back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(Modifier.padding(padding).padding(16.dp)) {

                OutlinedTextField(
                    value = "", // implement search logic later
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    placeholder = { Text("Search properties...") },
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip("All", true) {}
                    FilterChip("For sale", false) {}
                    FilterChip("For rent", false) {}
                }

                Spacer(Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                } else if (error != null) {
                    Text("Error: $error", color = Color.Red)
                } else {
                    LazyColumn {
                        items(listings) { house ->
                            PropertyCard(house,
                                onDelete = {
                                    scope.launch {
                                        viewModel.deleteHouse(house.id)
                                    }
                                },
                                onEdit = { /* Edit functionality */ }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (selected) Color(0xFF4D94FF) else Color.LightGray,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Text(
            text,
            color = if (selected) Color.White else Color.Black,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun PropertyCard(house: HouseListing, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // <- white card
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
//            if (house.listingPhotoPaths.isNotEmpty()) {
//                val imageUrl = house.listingPhotoPaths.first().replace("localhost", "10.0.2.2")
//                AsyncImage(
//                    model = imageUrl,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentScale = ContentScale.Crop
//                )

//            }

            Row(modifier = Modifier.padding(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text(house.title, fontWeight = FontWeight.Bold)
                    Text(house.streetAddress, style = MaterialTheme.typography.bodySmall)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(20),
                            color = if (true) Color(0xFFFFD5D5) else Color(0xFFE0E0F8),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                if (true) "For Sale" else "For Rent",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Text(
                            text = "ETB ${house.price}",
                            color = Color(0xFF1976D2),
                            fontWeight = FontWeight.Bold
                        )
                    }


                }
                Column(
                    Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(onClick = { /* star */ }) {
                        Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                    }
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Blue)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete" , tint = Color.Red)
                    }

                }


            }



        }
    }
}

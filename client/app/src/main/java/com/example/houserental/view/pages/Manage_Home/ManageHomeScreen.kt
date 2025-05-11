
package com.example.houserental.view.pages.manage_home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.Background
import com.example.houserental.ui.theme.BlackText
import com.example.houserental.ui.theme.BrandColor
import com.example.houserental.ui.theme.BrownText
import com.example.houserental.ui.theme.GoldButton
import com.example.houserental.ui.theme.RedButton
import com.example.houserental.view.components.CustomSearchBar
import com.example.houserental.viewModel.ManageHomeViewModelFactory
import compose.icons.fontawesomeicons.SolidGroup
import compose.icons.fontawesomeicons.solid.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageHomeScreen(
    onBack: () -> Unit,
    navController: NavController,

) {
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { ManageHomeViewModelFactory(repository) }
    val viewModel: ManageHomeViewModel = viewModel(factory = factory)

    val listings = viewModel.listings
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }


    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Manage Properties",color = BrandColor)},

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = BrandColor)
                    }
                },

                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White // <- white top bar
                        )
            )
        },
        content = { padding ->
            Column(Modifier.padding(padding).padding(16.dp).background(Background)) {

                CustomSearchBar(
                    value = searchText,
                    onValueChange = { searchText = it }
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("All", "For sale", "For rent").forEach { filter ->
                        FilterChip(
                            text = filter,
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                    }
                }


                Spacer(Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                } else if (error != null) {
                    Text("Error: $error", color = Color.Red)
                } else {
                    val filteredListings = listings.filter { house ->
                        when (selectedFilter) {
                            "For rent" -> house.type_id == 2
                            "For sale" -> house.type_id == 1
                            else -> true
                        }
                    }

                    LazyColumn {
                        items(filteredListings) { house ->

                        PropertyCard(house,
                                onDelete = {
                                    scope.launch {
                                        viewModel.deleteHouse(house.id)
                                    }
                                },
                                onEdit = { navController.navigate("edit_property/${house.id}") }
//

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
        color = if (selected) Color(0xFF4D94FF) else Color.White,
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

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        house.title,
                        fontWeight = FontWeight.Bold,
                        color = BlackText,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text("${house.city}, ${house.streetAddress}", fontSize = 14.sp, color = BrownText)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(25),
                            color = if (house.type_id == 1) Color(0xFFFFE5E5) else Color(0xFFDDEEFF),
                        ) {
                            Text(
                                if (house.type_id == 1) "For sale" else "For rent",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ETB ${house.price}",
                            color = Color(0xFF1976D2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                    }

                }
                Column(
                    Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF0F0F0),
                        modifier = Modifier.size(36.dp)
                    ) {
                        IconButton(onClick = { /* star */ }) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Star",
                                tint = GoldButton
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = BrandColor,
                        modifier = Modifier.size(36.dp)
                    ) {
                        IconButton(onClick = onEdit) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF0F0F0),
                        modifier = Modifier.size(36.dp)
                    ) {
                        IconButton(onClick = onDelete) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = RedButton
                            )
                        }
                    }

                }


            }



        }
    }
}

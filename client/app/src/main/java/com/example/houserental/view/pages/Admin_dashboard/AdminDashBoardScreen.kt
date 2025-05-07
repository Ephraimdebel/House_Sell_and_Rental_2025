package com.example.houserental.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.view.components.DashboardCard
import com.example.houserental.view.components.ManagementCard
import com.example.houserental.view.components.ValueCard
import com.example.houserental.viewModel.AdminDashboardViewModelFactory
import com.example.houserental.viewModel.DashboardViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChartLine
import compose.icons.fontawesomeicons.solid.DollarSign
import compose.icons.fontawesomeicons.solid.Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onAddPropertyClick: () -> Unit,
    onBack: () -> Unit
) {
    // Create the ApiService instance
    val apiService = RetrofitInstance.api

    // Create the HomeRepository instance by passing ApiService
    val repository = HomeRepository(apiService)

    // Create the ViewModel using the custom factory with the repository
    val viewModel: DashboardViewModel = viewModel(
        factory = AdminDashboardViewModelFactory(repository)
    )

    // Collect state from the ViewModel
    val totalProperties by viewModel.totalProperties.collectAsState()
    val propertiesForSale by viewModel.propertiesForSale.collectAsState()
    val propertiesForRent by viewModel.propertiesForRent.collectAsState()
    val totalSaleValue by viewModel.totalSaleValue.collectAsState()
    val monthlyRentalIncome by viewModel.monthlyRentalIncome.collectAsState()

    // Trigger the API call when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.loadTotalProperties(typeId = 1)
        viewModel.loadPropertiesForSale()
        viewModel.loadPropertiesForRent()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin DashBoard", color = Color(0xFF5D9DF0)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF7F7F7)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Scrollable content
        ) {
            Text("Welcome Back, Ephraim", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Admin Dashboard", color = Color.Gray)

            Spacer(Modifier.height(24.dp))
            Text("Overview", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            // Display total properties from ViewModel
            DashboardCard("Total properties", propertiesForRent + propertiesForSale,icon = FontAwesomeIcons.Solid.Home)
            DashboardCard("For Sale", propertiesForSale, color = Color.Red,icon = FontAwesomeIcons.Solid.DollarSign)
            DashboardCard("For Rent", propertiesForRent,icon = FontAwesomeIcons.Solid.Home)
            DashboardCard("Featured", 2, color = Color.Green,icon = FontAwesomeIcons.Solid.ChartLine)

            Spacer(Modifier.height(24.dp))
            Text("Property value", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()) {
                    ValueCard("Total sale value", "ETB ${totalSaleValue.toInt()}",modifier = Modifier.weight(1f))
                    ValueCard("Monthly Rental Income",
                        "ETB ${monthlyRentalIncome.toInt()}/mo", modifier = Modifier.weight(1f))
                }

            }

            Spacer(Modifier.height(24.dp))
            Text("Management", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ManagementCard("Manage properties", Icons.Default.Home, modifier = Modifier.weight(1f))
                ManagementCard("Manage users", Icons.Default.Person, modifier = Modifier.weight(1f),
//                    onIconClick = {
//                    navController.navigate("ManageUserScreen")} // Use your actual route
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onAddPropertyClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add new property")
            }
        }
    }
}


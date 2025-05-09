package com.example.houserental.view.pages.Search.SearchPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.houserental.view.PropertyCard
import com.example.houserental.viewModel.SearchViewModel

@Composable
fun SearchPage(
    navController: NavController, // Pass the NavController here
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    var typeId by remember { mutableStateOf("") }
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }

    val searchResults by searchViewModel.searchResults
    val searchError by searchViewModel.errorMessage
    val isLoading by searchViewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input fields
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = typeId,
            onValueChange = { typeId = it },
            label = { Text("Type ID (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = { minPrice = it },
                label = { Text("Min Price") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = maxPrice,
                onValueChange = { maxPrice = it },
                label = { Text("Max Price") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        searchViewModel.searchHomes(
                            city = city,
                            minPrice = minPrice.toIntOrNull(),
                            maxPrice = maxPrice.toIntOrNull(),
                            typeId = typeId.toIntOrNull()
                        )
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Button
        Button(
            onClick = {
                searchViewModel.searchHomes(
                    city = city,
                    minPrice = minPrice.toIntOrNull(),
                    maxPrice = maxPrice.toIntOrNull(),
                    typeId = typeId.toIntOrNull()
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Error Message
        searchError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Results
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchResults) { house ->
                PropertyCard(house = house, isHorizontal = false) {
                    // Navigate to Property Detail screen on click
                    navController.navigate("property_detail/${house.id}")
                }
            }
        }
    }
}

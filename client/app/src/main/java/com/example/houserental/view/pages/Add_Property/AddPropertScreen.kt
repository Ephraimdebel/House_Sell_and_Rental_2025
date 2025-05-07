package com.example.houserental.view.pages.add_property

//import AddPropertyViewModelFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.houserental.R
//import com.example.houserental.viewModel.AddPropertyViewModel
import java.io.File
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AssistChip
//import androidx.compose.material3.ChipDefaults
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.view.components.LabeledTextField
import com.example.houserental.view.components.SectionHeader
import com.example.houserental.view.components.SelectableChips
import com.example.houserental.viewModel.AddPropertyViewModel
import com.example.houserental.viewModel.AddPropertyViewModelFactory

import com.example.houserental.view.components.LabeledTextField
import com.example.houserental.view.components.SelectableChips
import com.example.houserental.view.components.ImagePickerComponent

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen() {
    Log.d("AddPropertyScreen", "Composables starting")

    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { AddPropertyViewModelFactory(repository) }
    val viewModel: AddPropertyViewModel = viewModel(factory = factory)


    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val imageUris = remember { mutableStateListOf<Uri>() }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            Log.d("ImagePicker", "Selected URIs: $uris")
            imageUris.clear()
            imageUris.addAll(uris)
            viewModel.setSelectedImages(uris)
        }
    )


    Scaffold (
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Add Property") },

                navigationIcon = {
                    IconButton(onClick = { /* navigate back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // <- white top bar
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                SectionHeader("Property Information")
                LabeledTextField(
                    label = "Title",
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it }
                )
                LabeledTextField(
                    label = "Description",
                    value = viewModel.description,
                    isMultiLine = true,
                    onValueChange = { viewModel.description = it }
                )

                Text("Property Type")
                SelectableChips(
                    options = listOf("House", "Apartment", "Condo", "Townhouse", "Villa"),
                    selected = viewModel.propertyType,
                    onSelected = { selectedOption ->
                        viewModel.propertyType = selectedOption
                    }
                )

                Text("Listing Type")
                SelectableChips(
                    options = listOf("For Sale", "For Rent"),
                    selected = viewModel.listingType,
                    onSelected = { selectedOption ->
                        viewModel.listingType = selectedOption
                    }
                )

                Spacer(Modifier.height(16.dp))
                SectionHeader("Price & Details")

                LabeledTextField(
                    label = "Price",
                    value = viewModel.price,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.price = it }
                )

                Row {
                    LabeledTextField(
                        label = "Bedrooms",
                        value = viewModel.bedroomCount,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f),
                        onValueChange = { viewModel.bedroomCount = it }
                    )
                    Spacer(Modifier.width(8.dp))
                    LabeledTextField(
                        label = "Bathrooms",
                        value = viewModel.bathroomCount,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f),
                        onValueChange = { viewModel.bathroomCount = it }
                    )
                }

                LabeledTextField(
                    label = "Area (sq ft)",
                    value = viewModel.area,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.area = it }
                )

                Spacer(Modifier.height(16.dp))
                SectionHeader("Location")

                LabeledTextField(
                    label = "Street Address",
                    value = viewModel.streetAddress,
                    onValueChange = { viewModel.streetAddress = it }
                )

                Row {
                    LabeledTextField(
                        label = "City",
                        value = viewModel.city,
                        modifier = Modifier.weight(1f),
                        onValueChange = { viewModel.city = it }
                    )
                    Spacer(Modifier.width(8.dp))
                    LabeledTextField(
                        label = "State",
                        value = viewModel.state,
                        modifier = Modifier.weight(1f),
                        onValueChange = { viewModel.state = it }
                    )
                }

                LabeledTextField(
                    label = "Zip Code",
                    value = viewModel.zipCode,
                    onValueChange = { viewModel.zipCode = it }
                )

                Spacer(Modifier.height(16.dp))
                SectionHeader("Amenities")
                val amenities = listOf(
                    "Wifi" to 1, "Parking" to 2, "Swimming pool" to 3, "Gym" to 4, "Air conditioning" to 5,
                    "Heating" to 6, "Laundry" to 7, "Security" to 8, "Elevator" to 9, "Garden" to 10
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    amenities.forEach { (name, id) ->
                        val selected = id in viewModel.facilities
                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.toggleFacility(id) },
                            label = { Text(name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF90CAF9),
                                containerColor = Color.LightGray,
                                labelColor = Color.Black,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                SectionHeader("Images")

                ImagePickerComponent(
                    imageUris = imageUris,
                    onImagesSelected = { uris ->
                        imageUris.clear()
                        imageUris.addAll(uris)
                        viewModel.setSelectedImages(uris)
                    }
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.submitProperty(context)
                        Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Property")
                }

                viewModel.message?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

    )

}
//
//@Composable
//fun SectionHeader(title: String) {
//    Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
//}
//
//@Composable
//fun LabeledTextField(label: String, value: String, keyboardType: KeyboardType = KeyboardType.Text, isMultiLine: Boolean = false, modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
//        modifier = modifier.fillMaxWidth(),
//        maxLines = if (isMultiLine) 4 else 1
//    )
//}
//
//@Composable
//fun OutlinedTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: @Composable () -> Unit,
//    keyboardOptions: KeyboardOptions,
//    modifier: Modifier = Modifier,
//    maxLines: Int = 1
//) {
//    androidx.compose.material3.OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = label,
//        keyboardOptions = keyboardOptions,
//        modifier = modifier,
//        maxLines = maxLines
//    )
//}
//
//
//
//@Composable
//fun SelectableChips(options: List<String>, selected: String, onSelected: (String) -> Unit) {
//    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        options.forEach { option ->
//            FilterChip(
//                selected = selected == option,
//                onClick = { onSelected(option) },
//                colors = FilterChipDefaults.filterChipColors(
//                    selectedContainerColor = Color(0xFF90CAF9)
//                ),
//                label = { Text(option) }
//            )
//
//        }
//    }
//}

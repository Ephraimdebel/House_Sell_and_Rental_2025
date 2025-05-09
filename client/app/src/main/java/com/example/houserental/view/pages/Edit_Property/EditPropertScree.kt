package com.example.houserental.view.pages.add_property

import EditPropertyViewModelFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.view.components.LabeledTextField
import com.example.houserental.view.components.SelectableChips
import com.example.houserental.view.components.ImagePickerComponent
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.houserental.ui.theme.BrandColor
import com.example.houserental.view.components.SectionHeader
import com.example.houserental.viewModel.EditPropertyViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditPropertyScreen(
    propertyId: Int,
    navController: NavController,
) {
    Log.d("EditPropertyScreen", "Composables starting")

    val context = LocalContext.current
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { EditPropertyViewModelFactory(repository) }
    val viewModel: EditPropertyViewModel = viewModel(factory = factory)

    // ✅ Load property by ID when screen is launched
    LaunchedEffect(Unit) {
        viewModel.loadPropertyById(propertyId,context)
    }

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

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = { Text("Edit Property") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
                Spacer(Modifier.height(16.dp))
                LabeledTextField(
                    label = "Title",
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it }
                )
                LabeledTextField(
                    label = "Description",
                    value = viewModel.description,
                    isMultiLine = true,
                    onValueChange = { viewModel.description = it },
                    modifier = Modifier.height(120.dp) // Adjust the height only for the Description field
                )
                Spacer(Modifier.height(16.dp))

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
                        val selected = viewModel.facilities
                                ?.split(",")
                            ?.mapNotNull { it.toIntOrNull() }
                            ?.contains(id) == true

//                        FilterChip(
//                            selected = selected,
//                            onClick = { viewModel.toggleFacility(id) },
//                            label = { Text(name) },
//                            colors = FilterChipDefaults.filterChipColors(
//                                selectedContainerColor = Color(0xFF90CAF9),
//                                containerColor = Color.LightGray,
//                                labelColor = Color.Black,
//                                selectedLabelColor = Color.White
//                            )
//                        )

                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.toggleFacility(id) },
                            label = { Text(name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandColor,
                                containerColor = Color.White,
                                labelColor = Color.Black,
                                selectedLabelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color.LightGray,
                                selectedBorderColor = Color.LightGray,
                                enabled = true,
                                selected = selected
                            )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                SectionHeader("Images")

                ImagePickerComponent(
                    imageUris = imageUris,
                    onImagesSelected = { uris ->
                        val updatedUris = uris.map { uri ->
                            val original = uri.toString()
                            val updated = original.replace("http://localhost", "http://10.0.2.2")
                            Log.d("ImageURI_REWRITE", "Original: $original → Updated: $updated")
                            Uri.parse(updated)
                        }

                        imageUris.clear()
                        imageUris.addAll(updatedUris)

                        viewModel.setSelectedImages(updatedUris) // ✅ Use updatedUris here
                    }
                )



                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.updateProperty(propertyId)
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


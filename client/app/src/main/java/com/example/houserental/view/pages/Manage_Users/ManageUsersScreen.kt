package com.example.houserental.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.houserental.data.repository.UserRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.viewModel.ManageUsersViewModel
import com.example.houserental.viewModel.ManageUsersViewModelFactory
import compose.icons.fontawesomeicons.RegularGroup
import compose.icons.fontawesomeicons.SolidGroup
import compose.icons.fontawesomeicons.solid.Phone
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.UserCheck
import compose.icons.fontawesomeicons.solid.UserMinus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(onBack: () -> Unit, navController: NavController) {
    val repository = remember { UserRepository(RetrofitInstance.api) }
    val factory = remember { ManageUsersViewModelFactory(repository) }
    val viewModel: ManageUsersViewModel = viewModel(factory = factory)

    val users = viewModel.users
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Users", color = Color(0xFF5D9DF0)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF7F7F7)
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = SolidGroup.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Search...")
                    }
                              },
                shape = RoundedCornerShape(16.dp), // Ensure rounded shape is set
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp)) // Add background
                    .clip(RoundedCornerShape(16.dp)) // Clip to the same shape
                    .padding(2.dp) // Optional, prevents edge clipping
            )

            Spacer(Modifier.height(16.dp))

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Error: $error", color = Color.Red)
                else -> {
                    Column (
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ){

                        users.filter {
                            it.fullName.contains(searchText.text, ignoreCase = true)
                        }.forEach { user ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                ,
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = rememberImagePainter("https://randomuser.me/api/portraits/men/${user.id}.jpg"),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(Modifier.width(1.dp))
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 24.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(user.fullName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                        Text(user.email, style = MaterialTheme.typography.bodyMedium)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = SolidGroup.Phone,
                                                modifier = Modifier.size(14.dp),
                                                contentDescription = "Phone Icon"
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = user.phoneNumber,
                                                style = MaterialTheme.typography.bodyMedium
                                            )

                                        }
                                        val (backgroundColor, textColor) = when (user.role.lowercase()) {
                                            "agent" -> Color(0xFFDFF5E1) to Color(0xFF1E824C)
                                            "guest" -> Color(0xFFE6F0FF) to Color(0xFF2D6DAB)
                                            "admin" -> Color(0xFFFDE8EF) to Color(0xFFB0005B)
                                            else -> Color.LightGray to Color.DarkGray
                                        }
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
                                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            Text(
                                                text = user.role,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = textColor
                                            )
                                        }
                                    }
                                    IconButton(onClick = { /* approve */ }) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp) // controls the button size
                                                .background(color = Color(0xFFE8E6E6), shape = RoundedCornerShape(50)) // rounded background
                                                .padding(6.dp) // padding around the icon
                                        ) {
                                            Icon(
                                                imageVector = SolidGroup.UserCheck,
                                                contentDescription = "Approve",
                                                tint = Color.Green,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }
                                    IconButton(onClick = { viewModel.deleteUser(user.id) }) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(color = Color(0xFFE8E6E6), shape = RoundedCornerShape(50))
                                                .padding(6.dp)
                                        ) {
                                            Icon(
                                                imageVector = SolidGroup.UserMinus,
                                                contentDescription = "Reject",
                                                tint = Color.Red,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

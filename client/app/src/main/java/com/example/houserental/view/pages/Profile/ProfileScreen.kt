package com.example.houserental.view

import androidx.compose.foundation.BorderStroke
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.view.components.ProfileSection
import com.example.houserental.viewModel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen( onGotoAdminDashboard: () -> Unit,) {
    val user = viewModel<ProfileViewModel>()

    val accountItems = listOf(
        "Personal Info" to Icons.Outlined.Person,
        "My Property" to Icons.Outlined.Home,
        "Favorites" to Icons.Outlined.FavoriteBorder,
        "Notification" to Icons.Outlined.Notifications
    )

    val settingItems = listOf(
        "App Setting" to Icons.Default.Settings,
        "Privacy & Security" to Icons.Default.Lock,
        "Help & Support" to Icons.Default.Share,
        "Logout" to Icons.Default.ExitToApp
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Text("Profile", fontSize = 20.sp, color = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
            )
        },
        //Ephraim modifiy this
        modifier = Modifier.padding(5.dp)
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        // Overall Box for the Profile Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F7))
                .padding(innerPadding)
        ) {
            // Column to display content with vertical scroll
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                // Profile card section with rounded corners and border
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Profile card content
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Welcome",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Sign in to manage your properties",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "and favorites",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Row for the Login and Sign Up buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick =  onGotoAdminDashboard,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4D91FF)),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Login", color = Color.White)
                            }

                            OutlinedButton(
                                onClick = { /* Handle Sign Up */ },
                                shape = RoundedCornerShape(50),
                                border = BorderStroke(1.dp, Color(0xFF4D91FF))
                            ) {
                                Text("Sign Up", color = Color(0xFF4D91FF))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Profile sections for account items
                ProfileSection("Account", accountItems) { selected ->
                    // Placeholder actions
                    println("Clicked on: $selected")
                }

                Spacer(Modifier.height(16.dp))

                // Profile sections for setting items
                ProfileSection("Settings", settingItems) { selected ->
                    // Placeholder actions
                    println("Clicked on: $selected")
                }
            }
        }
    }
}



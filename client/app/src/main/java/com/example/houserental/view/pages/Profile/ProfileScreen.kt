package com.example.houserental.view

import com.example.houserental.R
import android.util.Log
import androidx.compose.foundation.BorderStroke
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.houserental.data.repository.AuthRepository
import com.example.houserental.ui.theme.Background
import com.example.houserental.view.components.ProfileSection
import com.example.houserental.viewModel.ProfileViewModel
import com.example.houserental.viewModel.ProfileViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onGotoAdminDashboard: () -> Unit, navController: NavController) {
    val authRepository = AuthRepository(com.example.houserental.network.RetrofitInstance.api)
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(authRepository)
    )
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserData(context)
    }

    val userName by profileViewModel.userName
    val userEmail by profileViewModel.userEmail
    val userRole by profileViewModel.userRole
    val isLoggedIn by profileViewModel.isLoggedIn
    Log.d("UserData", "userName: ${profileViewModel.userRole.value}")
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
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)   //Apply white background at the root level
            .padding(5.dp)
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        // Overall Box for the Profile Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
        ) {
            // Column to display content with vertical scroll

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text("My Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(Modifier.height(1.dp))
                // Check if the user is logged in
                if (isLoggedIn) {
                    // User is logged in, display profile info
                    Spacer(Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Profile Image & Name Row
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Image(
                                    painter = painterResource(id= R.drawable.img),
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(80.dp) // reduced from 100.dp
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = userName ?: "Ephraim Debel",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }

                            // Email
                            Text(
                                text = userEmail ?: "david@admin.com",
                                fontSize = 14.sp,
                                color = Color.Black
                            )

                            // Role Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFD8EAFE))
                                    .padding(horizontal = 20.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = userRole ?: "Admin",
                                    color = Color(0xFF1565C0),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            }
                            if(userRole == "admin"){
                                // Admin Dashboard Button
                                OutlinedButton(
                                    onClick = { onGotoAdminDashboard() },
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, Color(0xFF1565C0)),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF1565C0)
                                    )
                                ) {
                                    Text("Admin Dashboard", fontSize = 14.sp)
                                }
                            }else{
                                /*userdashboard */
                            }

                        }
                    }


                } else {
                    // User is not logged in, show login and signup options
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(24.dp))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
                                    onClick = { navController.navigate("login") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4D91FF)),
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier
                                        .height(42.dp)
                                        .weight(1f) // Make it expand equally
                                        .padding(end = 8.dp) // spacing between buttons
                                ) {
                                    Text("Login", color = Color.White, fontSize = 15.sp)
                                }

                                OutlinedButton(
                                    onClick = { navController.navigate("signup") },
                                    shape = RoundedCornerShape(50),
                                    border = BorderStroke(1.dp, Color(0xFF4D91FF)),
                                    modifier = Modifier
                                            .height(42.dp)
                                            .weight(1f) // Make it expand equally
                                            .padding(end = 8.dp) // spacing between buttons
                                ) {
                                    Text("Sign Up", color = Color(0xFF4D91FF))
                                }
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
                    if (selected == "Logout") {
                        // Perform logout
                        profileViewModel.logout(context)
                    } else {
                        println("Clicked on: $selected")
                    }
                }
            }
        }
    }
}



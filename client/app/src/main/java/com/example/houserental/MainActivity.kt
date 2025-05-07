package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.AdminDashboardScreen
import com.example.houserental.view.ManageUsersScreen
import com.example.houserental.view.ProfileScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
import com.example.houserental.viewModel.AdminDashboardViewModelFactory
import com.example.houserental.viewModel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseRentalTheme {
//                ManageHomeScreen()
//                ManageUsersScreen(
//                    onBack = { finish() }
//                )
                AdminDashboardScreen(
                    onAddPropertyClick = { /* nav logic */ },
                    onBack = { finish() }
                    )
//                Scaffold{ paddingValues ->
//                    ProfileScreen(paddingValues = paddingValues)
//                }

                }
            }
        }
    }
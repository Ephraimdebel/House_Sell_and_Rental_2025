package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.enableEdgeToEdge
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.HomeScreen
import com.example.houserental.view.pages.add_property.AddPropertyScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.ManageHomeViewModel
import com.example.onlytry.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = HomeViewModel(
            repository = HomeRepository(RetrofitInstance.api)
        )
        setContent {
            HouseRentalTheme {
//                RentalApp()

//                ManageHomeScreen()
//                AddPropertyScreen()
                HomeScreen(viewModel)

                }
            }

        }
    }



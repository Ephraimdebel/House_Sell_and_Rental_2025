package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.AdminDashboardScreen
import com.example.houserental.view.ManageUsersScreen
import com.example.houserental.view.ProfileScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
// if you've created this
import com.example.houserental.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseRentalTheme {
                AppNavigation()
            }

        }
    }
}

package com.example.houserental.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.houserental.view.HomeScreen
import com.example.houserental.view.ProfileScreen
import com.example.houserental.view.components.BottomNavBar
import com.example.houserental.viewModel.HomeViewModel

@Composable
fun UserNavGraph(navController: NavHostController) {
    val bottomNavItems = listOf("home", "search", "favorite", "profile")
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentDestination in bottomNavItems) {
                BottomNavBar(navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(it)
        ) {
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("search") { /* "SearchScreen(navController) " */}
            composable("favorite") { /*FavoriteScreen(navController) */}
            composable("profile") {
                ProfileScreen(
                    onGotoAdminDashboard = {
                        navController.navigate("admin_dashboard_root")
                    },
                    navController = navController
                )

            }
        }
    }
}

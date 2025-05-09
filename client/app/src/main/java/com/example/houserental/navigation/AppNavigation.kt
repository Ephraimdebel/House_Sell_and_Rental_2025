package com.example.houserental.navigation

import com.example.houserental.view.pages.Search.SearchPage
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.houserental.view.*
import com.example.houserental.view.components.BottomNavBar
import com.example.houserental.view.pages.add_property.*
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
import com.example.houserental.ui.pages.home_detail.PropertyDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define which screens should show the BottomNavBar
    val bottomNavItems = listOf("home", "search", "favorite", "profile")

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavItems) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // === Root User Pages ===
            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("search") {
                SearchPage(navController = navController) // Navigating to SearchPage
            }

            composable("favorite") {
                // TODO: Replace with your actual FavoriteScreen()
            }

            composable("profile") {
                ProfileScreen(
                    onGotoAdminDashboard = {
                        navController.navigate("admin_dashboard")
                    }
                )
            }

            // === Admin Pages ===
            composable("admin_dashboard") {
                AdminDashboardScreen(
                    onAddPropertyClick = { navController.navigate("add_property") },
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable("add_property") {
                AddPropertyScreen(navController = navController)
            }

            composable("manage_property") {
                ManageHomeScreen(
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable("manage_users") {
                ManageUsersScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "edit_property/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                if (id != null) {
                    EditPropertyScreen(navController = navController, propertyId = id)
                }
            }

            composable(
                route = "property_detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                if (id != null) {
                    PropertyDetailScreen(
                        navController = navController,
                        houseId = id
                    )
                }
            }
        }
    }
}

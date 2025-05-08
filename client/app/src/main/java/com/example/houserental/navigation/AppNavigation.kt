package com.example.houserental.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
<<<<<<< HEAD
import com.example.houserental.ui.pages.home_detail.PropertyDetailScreen
import com.example.houserental.view.AdminDashboardScreen
import com.example.houserental.view.ManageUsersScreen
import com.example.houserental.view.pages.add_property.AddPropertyScreen
import com.example.houserental.view.pages.add_property.EditPropertyScreen
=======
import com.example.houserental.view.*
import com.example.houserental.view.components.BottomNavBar
import com.example.houserental.view.pages.add_property.*
>>>>>>> dac770d108272ce1041840a1ac31a8f79376fbcc
import com.example.houserental.view.pages.manage_home.ManageHomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

<<<<<<< HEAD
    NavHost(navController = navController, startDestination = "admin_dashboard") {
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

        composable(
            "edit_property/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                EditPropertyScreen(navController = navController, propertyId = id)
            }
        }

        composable("manage_users") {
            ManageUsersScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        // Property Detail Screen route added here
        composable(
            "property_detail/{houseId}",
            arguments = listOf(navArgument("houseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val houseId = backStackEntry.arguments?.getInt("houseId") ?: 0
            PropertyDetailScreen(houseId = houseId, navController = navController)
        }
=======
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
                HomeScreen()
            }

            composable("search") {
                // TODO: Replace with your actual SearchScreen()
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
        }
>>>>>>> dac770d108272ce1041840a1ac31a8f79376fbcc
    }
}

package com.example.houserental.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.houserental.ui.pages.home_detail.PropertyDetailScreen
import com.example.houserental.view.AdminDashboardScreen
import com.example.houserental.view.ManageUsersScreen
import com.example.houserental.view.pages.add_property.AddPropertyScreen
import com.example.houserental.view.pages.add_property.EditPropertyScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

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
    }
}

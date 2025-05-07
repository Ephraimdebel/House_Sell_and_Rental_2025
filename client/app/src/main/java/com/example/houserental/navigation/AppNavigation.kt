package com.example.houserental.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.houserental.view.pages.add_property.EditPropertyScreen

import com.example.houserental.view.pages.manage_home.ManageHomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "manage_property") {
        composable("manage_property") {
            ManageHomeScreen(navController = navController)
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
}

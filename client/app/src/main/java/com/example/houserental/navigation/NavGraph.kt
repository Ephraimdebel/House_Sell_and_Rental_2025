package com.example.houserental.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.compose.runtime.Composable
import com.example.houserental.view.pages.add_property.EditPropertyScreen

// import your other screens...

const val EDIT_PROPERTY_ROUTE = "edit_property/{propertyId}"

fun NavController.navigateToEditProperty(propertyId: Int) {
    navigate("edit_property/$propertyId")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home" // or whatever your home screen is
    ) {
        // Other composable screens...

        composable(
            route = EDIT_PROPERTY_ROUTE,
            arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: return@composable
            EditPropertyScreen(propertyId = propertyId, navController = navController)
        }
    }
}

package com.example.houserental.navigation


import SearchScreen
import SignUpViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.houserental.LoginScreen
import com.example.houserental.data.repository.AuthRepository
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.view.*
import com.example.houserental.view.components.BottomNavBar
import com.example.houserental.view.pages.add_property.*
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
import com.example.houserental.ui.pages.home_detail.PropertyDetailScreen
import com.example.houserental.view.pages.Favorite.FavoriteScreen
//import com.example.houserental.view.pages.Search.SearchPage.SearchPage
//import com.example.houserental.view.pages.Search.SearchPage.SearchPage
import com.example.houserental.viewModel.LoginViewModel
import com.example.houserental.viewModel.LoginViewModelFactory
import com.example.houserental.viewModel.SignUpViewModelFactory
import com.example.onlytry.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define which screens should show the BottomNavBar
    val bottomNavItems = listOf("home", "search", "favorite", "profile")


    val homeRepositoryInstance = HomeRepository(RetrofitInstance.api)

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

//            composable("search") {
//                SearchPage(navController = navController) // Navigating to SearchPage
//            }

            composable("favorite") {
                FavoriteScreen(navController = navController)
            }

            composable("profile") {
                ProfileScreen(
                    onGotoAdminDashboard = {
                        navController.navigate("admin_dashboard")
                    },
                    navController = navController
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
            composable("login") {
                // Step 1: Initialize the API service and repository
                val apiService = RetrofitInstance.api
                val repository = AuthRepository(apiService)

                // Step 2: Create the ViewModel using factory
                val viewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(repository)
                )

                // Step 3: Pass the viewModel to LoginScreen
                LoginScreen(
                    onBackClick = { navController.popBackStack() },
                    onSignUpClick = { navController.navigate("signup") },
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    viewModel = viewModel
                )
            }

            composable("signup") {
                // Step 1: Initialize the API service and repository
                val apiService = RetrofitInstance.api
                val repository = AuthRepository(apiService)

                // Step 2: Create the ViewModel using factory
                val viewModel: SignUpViewModel = viewModel(
                    factory = SignUpViewModelFactory(repository)
                )

                SignUpScreen(
                    onBackClick = { navController.popBackStack() },
                    onLoginClick = { navController.navigate("login") },
                    viewModel = viewModel
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
            composable("search") {
                SearchScreen(navController = navController)
            }
        }
    }
}

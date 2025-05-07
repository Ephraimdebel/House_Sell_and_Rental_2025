package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.houserental.ui.theme.HouseRentalTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseRentalTheme {
                RentalApp()

            }

        }
    }
}
@Composable
fun RentalApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onBackClick = { /* Handle back press - could finish activity */ },
                onSignUpClick = { navController.navigate("signup") },
                onLoginSuccess = {
                    // Navigate to main app screen after successful login
                    // navController.navigate("home") { popUpTo("login") }
                }
            )
        }

        composable("signup") {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.navigate("login") { popUpTo("signup") } }
            )
        }
    }
}



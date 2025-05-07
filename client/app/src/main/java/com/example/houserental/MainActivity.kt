package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< HEAD
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
=======
import androidx.activity.enableEdgeToEdge
>>>>>>> f44af312cbb8836610d22ae537ded3522c539bfe
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.pages.add_property.AddPropertyScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseRentalTheme {
<<<<<<< HEAD
                RentalApp()

=======
//                ManageHomeScreen()
                AddPropertyScreen()
                }
>>>>>>> f44af312cbb8836610d22ae537ded3522c539bfe
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
<<<<<<< HEAD
    }
}


=======
    }
>>>>>>> f44af312cbb8836610d22ae537ded3522c539bfe

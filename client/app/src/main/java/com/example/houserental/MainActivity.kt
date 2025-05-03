package com.example.houserental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.pages.manage_home.ManageHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseRentalTheme {
                ManageHomeScreen()
                }
            }
        }
    }
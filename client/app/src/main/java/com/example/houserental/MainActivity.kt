package com.example.houserental

import SignUpViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.houserental.data.repository.AuthRepository
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.LoginViewModel
import com.example.houserental.viewModel.SignUpViewModelFactory
import com.example.onlytry.SignUpScreen
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitInstance.api
        val authRepository = AuthRepository(apiService)
        val signUpViewModelFactory = SignUpViewModelFactory(authRepository)

        setContent {
            HouseRentalTheme {
                val signUpViewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = signUpViewModelFactory
                )

                SignUpScreen(
                    onBackClick = { /* handle back nav */ },
                    onLoginClick = { /* handle login nav */ },
                    viewModel = signUpViewModel
                )
            }
        }
    }
}




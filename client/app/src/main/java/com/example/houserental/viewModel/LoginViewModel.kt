package com.example.houserental.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)
        private set

    var token by mutableStateOf("")
        private set

    fun login(email: String, password: String,context: Context) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            loginSuccess = false // reset
            val result = repository.login(email, password,context)

            isLoading = false
            result.onSuccess {
                token = it.token
                loginSuccess = true
            }.onFailure {
                Log.d("LoginError", "${it.message}")
                errorMessage = it.message
            }
        }
    }

    fun resetLoginSuccess() {
        loginSuccess = false
    }
}


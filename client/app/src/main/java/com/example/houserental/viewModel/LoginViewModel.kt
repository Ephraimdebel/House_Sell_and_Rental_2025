package com.example.houserental.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var token by mutableStateOf("")
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.login(email, password)
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
}

package com.example.houserental.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.AuthRepository
import com.example.houserental.preferences.UserPreferences
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userName = mutableStateOf<String?>(null)
    val userName: State<String?> = _userName

    private val _userEmail = mutableStateOf<String?>(null)
    val userEmail: State<String?> = _userEmail

    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> = _userRole

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    fun fetchUserData(context: Context) {
        viewModelScope.launch {
            UserPreferences.isLoggedIn(context).collect { loggedIn ->
                _isLoggedIn.value = loggedIn
                if (loggedIn) {
                    UserPreferences.getUserDetails(context).collect { user ->
                        _userName.value = user.username
                        _userRole.value = user.role
                        _userEmail.value = user.email
                    }
                }
            }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            UserPreferences.logout(context)
            _isLoggedIn.value = false
            _userName.value = null
            _userEmail.value = null
            _userRole.value = null
        }
    }
}

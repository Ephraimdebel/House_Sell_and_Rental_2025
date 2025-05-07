
package com.example.houserental.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.model.User
import com.example.houserental.data.repository.UserRepository
import kotlinx.coroutines.launch

class ManageUsersViewModel(private val repository: UserRepository) : ViewModel() {

    var users by mutableStateOf<List<User>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.fetchAllUsers()
            isLoading = false

            result
                .onSuccess { users = it.users }
                .onFailure { errorMessage = it.message }
        }
    }
    fun deleteUser(id: Int) {
        viewModelScope.launch {
            val success = repository.deleteUser(id)
            if (success) {
                users = users.filterNot { it.id == id }
            } else {
                errorMessage = "Failed to delete user."
            }
        }
    }

}

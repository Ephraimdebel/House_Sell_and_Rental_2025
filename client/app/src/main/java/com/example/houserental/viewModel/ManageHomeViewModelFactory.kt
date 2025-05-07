// File: viewModel/ManageHomeViewModelFactory.kt
package com.example.houserental.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.houserental.data.repository.HomeRepository

class ManageHomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ManageHomeViewModel(repository) as T
    }
}

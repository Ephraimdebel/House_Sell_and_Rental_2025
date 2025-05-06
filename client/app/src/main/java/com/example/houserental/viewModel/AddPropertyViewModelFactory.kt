package com.example.houserental.viewModel

//import AddPropertyViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.houserental.data.repository.HomeRepository

class AddPropertyViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddPropertyViewModel(repository) as T
    }
}

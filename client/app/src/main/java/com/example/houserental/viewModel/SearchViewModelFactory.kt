//package com.example.houserental.viewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class SearchViewModelFactory(
//    private val repository: SearchRepository
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
//            return SearchViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

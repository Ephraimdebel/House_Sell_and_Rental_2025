import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.viewModel.EditPropertyViewModel

class EditPropertyViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditPropertyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

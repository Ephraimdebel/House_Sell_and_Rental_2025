import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houserental.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var successMessage by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun register(fullName: String, email: String, password: String, phone: String) {
        isLoading = true
        successMessage = null
        errorMessage = null

        viewModelScope.launch {
            val result = repository.registerUser(fullName, email, password, phone)
            result
                .onSuccess { successMessage = it.message }
                .onFailure { errorMessage = it.localizedMessage ?: "Registration failed" }

            isLoading = false
        }
    }
}

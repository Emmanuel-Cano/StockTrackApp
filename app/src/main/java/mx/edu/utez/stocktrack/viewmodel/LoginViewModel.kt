package mx.edu.utez.stocktrack.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utez.stocktrack.viewmodel.UserRepository
import kotlinx.coroutines.launch
import mx.edu.utez.stocktrack.data.network.RetrofitInstance

class LoginViewModel : ViewModel() {

    private val repository = UserRepository(RetrofitInstance.api)

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoginSuccess by mutableStateOf(false)

    fun onLoginClick() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Ingresa email y contraseña"
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            val result = repository.login(email, password)
            isLoading = false

            result.onSuccess {
                isLoginSuccess = true
            }.onFailure { error ->
                errorMessage = error.message ?: "Correo o contraseña incorrecta"
            }
        }
    }
    var userEmail: String? by mutableStateOf(null)
    var userToken: String? by mutableStateOf(null)

    fun clearSession() {
        userEmail = null
        email = ""
        password = ""
        userToken = null
        isLoginSuccess = false
    }
}
